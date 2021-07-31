package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.service.quiz.dao.QuizDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizLogDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateTypeDaoService;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizPickService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuizStateTypeDaoService quizStateTypeDaoService;
    private final QuizStateDaoService quizStateService;
    private final QuizLogDaoService quizLogDaoService;
    private final QuizDaoService quizDaoService;
    private final UserDaoService userDaoService;

    /**
     * desc: Do Pick random problems
     * param
     * userSeq         user_table pk value
     * problem_qtt	    int	1~5
     * problem_lv	    String	silver/SILVER or gold/GOLD
     */
    @Transactional
    public List<QuizState> pickRandomProblems(final int userSeq, final int problemCount) {
        checkArgument(1 <= problemCount && problemCount <= 5, "problemCount must be 1 ~ 5");

        final User user = userDaoService.findById(userSeq);
        return getUsersRandomProblems(user, problemCount);
    }

    private List<QuizState> getUsersRandomProblems(final User user, final int problemCount) {
        checkArgument(user != null, "user must be not null");

        /**
         *   Business Logic
         *
         *   if (notPickedProblems.isEmpty()) {
         *       //npns pick
         *       //nps pick
         *   } else {
         *       //안 뽑은 문제 Pick
         *       //npns pick
         *       //nps pick
         *   }
         */
        //메서드의 결과값. results 값은 (사용자가 한 번도 안 뽑은 문제 + npns + pns) 문제들로 구성되어있다.
        List<QuizState> results = new LinkedList<>();

        //problemCount를 restProblemCnt로 관리하여 (사용자가 한 번도 안 뽑은 문제 + npns + pns)로 problemCount 갯수를 맞춘다.
        int restProblemCnt = problemCount;

        //사용자가 문제 셋에서 한 번도 뽑은 적 없는 문제. 여기에 해당하는 문제가 있는지 가장 먼저 조회한다.
        final List<Quiz> notPickedProblems = quizDaoService.findAllNotPickedProblems(user);

        if (!notPickedProblems.isEmpty()) {
            //아직 Quiz Table에서 안 뽑은 문제가 있는 경우(npns 상태가 아님)
            //1st. 문제 set에서 안 뽑은 문제 Pick
            final List<Quiz> candidateProblems = getCandidateProblems(notPickedProblems);
            final List<Quiz> pickProblems;

            if (candidateProblems.size() <= restProblemCnt) {
                logger.debug("[notPickedProblem if] candidateProblems.size(): {}, restProblemCnt: {}", candidateProblems.size(), restProblemCnt);
                //문제 set에서 안 뽑은 문제가 사용자가 뽑기로 한 문제 갯수보다 작은 경우 => List 그대로 반환
                pickProblems = candidateProblems;
            } else {
                logger.debug("[notPickedProblem else] candidateProblems.size(): {}, restProblemCnt: {}", candidateProblems.size(), restProblemCnt);
                //문제 set에서 안 뽑은 문제가 사용자가 뽑기로 한 문제 갯수보다 많은 경우 => List 중 일부를 랜덤으로 뽑음
                pickProblems = getRandomProblems(candidateProblems, restProblemCnt);
            }

            //Quiz Table에서 안 뽑은 문제들 이므로 quiz_state Table에 NOT_SELECTED 상태로 저장
            results = saveNewData(user, QuizStateTypeEnum.NOT_SELECTED, pickProblems);

            //Quiz Table에서 새로 뽑은 문제들로 사용자가 뽑기로 한 문제가 전부 채워졌다면 return
            if (restProblemCnt == results.size()) {
                return results;
            } else {
                //Quiz Table에서 문제를 새로 다 뽑았지만, 사용자가 뽑기로 한 문제보다 부족한 경우 npns, pns 뽑으로 다음로직으로 이동
                restProblemCnt -= results.size();
            }
        }

        //Quiz Table에서 안 뽑은 문제가 없는 경우 => quiz_state 테이블에서 npns, pns 순서로 문제를 뽑아본다.

        //2nd. npns pick
        //quiz_state 테이블에서 특정 사용자의 문제 상태 중 npns인 List를 가져온다.
        final List<QuizState> npnsProblems = quizStateService.getProblemsOfQSTState(user, QuizStateTypeEnum.NOT_PICKED);

        if (npnsProblems.size() < restProblemCnt) {
            logger.debug("[npns if] npnsProblems.size(): {}, restProblemCnt: {}", npnsProblems.size(), restProblemCnt);
            //quiz_state 테이블에서 npns 상태인 문제가 사용자가 뽑기로 한 문제 갯수보다 적은 경우
            //npns 문제 갯수만큼 뽑아야 할 문제 갯수에서 차감하고 results에 추가한다.
            restProblemCnt -= npnsProblems.size();
            results = Stream.concat(results.stream(), npnsProblems.stream()).collect(toList());
        } else {
            logger.debug("[npns else] npnsProblems.size(): {}, restProblemCnt: {}", npnsProblems.size(), restProblemCnt);
            //quiz_state 테이블에서 npns 상태인 문제가 사용자가 뽑기로 한 문제 갯수보다 많은 경우 => List 중에 일부를 랜덤으로 뽑는다.
            final List<QuizState> updateList = getRandomProblemsState(npnsProblems, restProblemCnt);

            //npns 상태인 문제들을 => pns 상태로 갱신하고
            update(updateList, QuizStateTypeEnum.NOT_SELECTED);

            //results에 추가 후 결과 리턴
            results = Stream.concat(results.stream(), updateList.stream()).collect(toList());
            return results;
        }

        //3rd. nps pick
        final List<QuizState> pnsProblems = quizStateService.getProblemsOfQSTState(user, QuizStateTypeEnum.NOT_SOLVED);

        //nps를 뽑은 후 npns 상태였던 문제들을 pns 상태로 갱신
        //이 코드가 실행되는 시점은 아직 사용자에게 돌려줄 문제 갯수가 남아있는 상황(pns 상태인 문제도 선택해서 돌려줘야 하는 경우)
        //npns 상태 update를 pns를 뽑기전에 해버리면, npns => pns가 된 문제들도 pns 리스트로 내려올 수 있음
        update(npnsProblems, QuizStateTypeEnum.NOT_SELECTED);

        if (pnsProblems.size() < restProblemCnt) {
            logger.debug("[pns if] pnsProblems.size(): {}, restProblemCnt: {}", pnsProblems.size(), restProblemCnt);

            //quiz_state 테이블에서 pns 상태인 문제가 사용자가 뽑기로 한 문제 갯수보다 적은 경우 => List에 그대로 추가
            results = Stream.concat(results.stream(), pnsProblems.stream()).collect(toList());
        } else {
            logger.debug("[pns else] pnsProblems.size(): {}, restProblemCnt: {}", pnsProblems.size(), restProblemCnt);

            //quiz_state 테이블에서 pns 상태인 문제가 사용자가 뽑기로 한 문제 갯수보다 많은 경우 => List에서 일부를 랜덤으로 뽑는다.
            final List<QuizState> result = getRandomProblemsState(pnsProblems, restProblemCnt);
            results = Stream.concat(results.stream(), result.stream()).collect(toList());
        }

        //(사용자가 한 번도 안 뽑은 문제 + npns + pns) 결과 리턴
        return results;
    }

    private List<QuizState> saveNewData(User user, QuizStateTypeEnum quizStateTypeEnum, List<Quiz> pickProblems) {
        checkArgument(user != null, "user must be not null");
        checkArgument(pickProblems != null, "pickProblems must be not null");

        if (pickProblems.isEmpty()) {
            return emptyList();
        } else {

            List<QuizState> quizStates = new LinkedList<>();

            //QuizStateType 얻기
            final QuizStateType quizStateType = quizStateTypeDaoService.findByDesc(quizStateTypeEnum);

            for (Quiz problem : pickProblems) {
                //saveNewQuizState
                QuizState quizState = quizStateService.save(user, quizStateType, problem);
                //saveNewQuizLog
                quizLogDaoService.save(user, quizStateType, problem);
                quizStates.add(quizState);
            }
            return quizStates;
        }
    }

    /**
     * quizStates 리스트의 QuizStateType 값을 quizStateTypeEnum 값으로 갱신
     */
    private List<QuizState> update(List<QuizState> quizStates, QuizStateTypeEnum quizStateTypeEnum) {
        checkArgument(quizStates != null, "List<QuizState> must be not null");

        if (quizStates.isEmpty()) {
            return Collections.emptyList();
        }

        QuizStateType quizStateType = quizStateTypeDaoService.findByDesc(quizStateTypeEnum);

        for (QuizState quizState : quizStates) {
            quizState.updateQuizStateType(quizStateType);
        }

        return quizStates;
    }

    /**
     * 사용자가 뽑지 않은 문제 List 중에서 problemLevel 에 해당하는 문제들만 반환.
     */
    private List<Quiz> getCandidateProblems(List<Quiz> problems) {
        checkArgument(problems != null, "problems must be not null");

        if (problems.isEmpty()) {
            return emptyList();
        } else {
            return problems;
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
}
