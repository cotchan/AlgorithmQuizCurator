package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.*;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizRepository;
import com.yhcy.aqc.repository.quiz.QuizStateRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizPickService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuizStateTypeService quizStateTypeService;
    private final QuizRepository quizRepository;
    private final QuizStateRepository quizStateRepository;
    private final UserRepository userRepository;

    /**
     * desc: Do Pick random problems
     * param
     * userSeq         user_table pk value
     * problem_qtt	    int	1~5
     * problem_lv	    String	silver/SILVER or gold/GOLD
     */
    @Transactional
    public List<QuizState> pickRandomProblems(int userSeq, int problemCount, String problemLevel) throws Exception {
        checkArgument(1 <= problemCount && problemCount <= 5, "problemCount must be 1 ~ 5");
        checkArgument(problemLevel != null, "problemLevel must be not null");
        checkArgument(problemLevel.toUpperCase().equals(QuizLevel.SILVER.value())
                        || problemLevel.toUpperCase().equals(QuizLevel.GOLD.value()),
                "problemLevel must be silver/SILVER or gold/GOLD");

        return userRepository.findById(userSeq).map(findUser -> {
            return getRandomProblems(findUser, problemCount, problemLevel);

            //FIXME: Exception handling
        }).orElseThrow(() -> new Exception("Hello World"));
    }

    private List<QuizState> getRandomProblems(User user, int problemCount, String problemLevel) {
        checkArgument(user != null, "user must be not null");

        final List<Quiz> notPickedProblems = quizRepository.findAllNotPickedProblems(user);

        /**
         *   if (notPickedProblems.isEmpty()) {
         *       //npns pick
         *       //nps pick
         *   } else {
         *       //안 뽑은 문제 Pick
         *       //npns pick
         *       //nps pick
         *   }
         */

        int restProblemCnt = problemCount;
        List<QuizState> result = new LinkedList<>();

        if (!notPickedProblems.isEmpty()) {
            //문제 set에서 안 뽑은 문제 Pick
            final List<Quiz> candidateProblems = getCandidateProblems(notPickedProblems, problemLevel);
            final List<Quiz> pickProblems;

            if (candidateProblems.size() <= restProblemCnt) {
                pickProblems = candidateProblems;
            } else {
                pickProblems = getRandomProblems(candidateProblems, restProblemCnt);
            }

            result = saveNewQuizState(user, QuizStateTypeEnum.NOT_SELECTED, pickProblems);

            if (restProblemCnt == result.size()) {
                return result;
            } else {
                restProblemCnt -= result.size();
            }
        }

        //npns pick
        List<QuizState> npnsProblems = getProblemsOfQSTState(user, QuizStateTypeEnum.NOT_PICKED);

        if (npnsProblems.size() < restProblemCnt) {
            restProblemCnt -= npnsProblems.size();
            update(npnsProblems, QuizStateTypeEnum.NOT_SELECTED);
            result.addAll(npnsProblems);
        } else {
            List<QuizState> updateList = getRandomProblemsState(npnsProblems, restProblemCnt);
            update(updateList, QuizStateTypeEnum.NOT_SELECTED);
            result.addAll(updateList);
            return result;
        }

        //nps pick
        List<QuizState> pnsProblems = getProblemsOfQSTState(user, QuizStateTypeEnum.NOT_SOLVED);

        if (pnsProblems.size() < restProblemCnt) {
            result.addAll(pnsProblems);
        } else {
            result.addAll(getRandomProblemsState(pnsProblems, restProblemCnt));
        }
        return result;
    }

    private List<QuizState> saveNewQuizState(User user, QuizStateTypeEnum quizStateTypeEnum, List<Quiz> pickProblems) {
        checkArgument(user != null, "user must be not null");
        checkArgument(pickProblems != null, "pickProblems must be not null");

        if (pickProblems.isEmpty()) {
            return emptyList();
        } else {

            List<QuizState> quizStates = new LinkedList<>();

            //QuizStateType 얻기
            final QuizStateType quizStateType = quizStateTypeService.findByDesc(quizStateTypeEnum);

            for (Quiz problem : pickProblems) {
                QuizState newQuizState = QuizState.builder()
                        .user(user)
                        .quiz(problem)
                        .quizStateType(quizStateType)
                        .build();

                QuizState quizState = quizStateRepository.save(newQuizState);
                quizStates.add(quizState);
            }

            return quizStates;
        }
    }

    /**
     * quizStates 리스트의 QuizStateType 값을 quizStateTypeEnum 값으로 갱신
     */
    private List<QuizState> update(List<QuizState> quizStates, QuizStateTypeEnum quizStateTypeEnum) {

        QuizStateType quizStateType = quizStateTypeService.findByDesc(quizStateTypeEnum);

        for (QuizState quizState : quizStates) {
            quizState.updateQuizStateType(quizStateType);
        }

        return quizStates;
    }

    /**
     * 사용자가 뽑지 않은 문제 List 중에서 problemLevel 에 해당하는 문제들만 반환.
     */
    private List<Quiz> getCandidateProblems(List<Quiz> problems, String problemLevel) {

        checkArgument(problems != null, "problems must be not null");

        if (problems.isEmpty()) {
            return emptyList();
        }

        if (problemLevel.toUpperCase().equals(QuizLevel.SILVER.value())) {
            return problems.stream().filter(problem -> problem.getQuizLevel() == QuizLevel.SILVER).collect(toList());
        } else {
            return problems.stream().filter(problem -> problem.getQuizLevel() == QuizLevel.GOLD).collect(toList());
        }
    }

    /**
     * problems 셔플 후 problemCount 갯수만큼만 문제를 선택한다.
     */
    private List<Quiz> getRandomProblems(List<Quiz> problems, int problemCount) {
        checkArgument(problems.size() >= problemCount, "problems size must be greater than problemCount.");
        shuffle(problems);
        return problems.subList(0, problemCount);
    }

    /**
     * 기존에 저장되어 있던 QuizState 셔플 후 problemCount 갯수만큼만 문제를 선택한다.
     */
    private List<QuizState> getRandomProblemsState(List<QuizState> problems, int problemCount) {
        checkArgument(problems.size() >= problemCount, "problems size must be greater than problemCount.");
        shuffle(problems);
        return problems.subList(0, problemCount);
    }

    /**
     * Get problems of QuizStateTypeState
     * QST == QuizStateType를 의미한다.
     * NPNS, PNS, PS 중 해당하는 상태의 정보를 가져온다.
     */
    private List<QuizState> getProblemsOfQSTState(User user, QuizStateTypeEnum quizStateType) {
        return quizStateRepository.findAllOfTypeStateProblems(user, quizStateType.state());
    }

    //FIXME
    //for text
    public void testFunc() {

        int userSeq = 25;
        userRepository.findById(userSeq).map(findUser -> {
            List<QuizState> qs = quizStateRepository.findAllOfTypeStateProblems(findUser, QuizStateTypeEnum.NOT_SOLVED.state());

            for (QuizState quizState : qs) {
                logger.info("quiz url = {}", quizState.getQuiz().getRefSiteUrl());
                logger.info("quizStateType state  = {}", quizState.getQuizStateType().getDesc());
            }

            return true;
        });
    }


}
