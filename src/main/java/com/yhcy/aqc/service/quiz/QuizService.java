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

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuizStateTypeService quizStateTypeService;
    private final QuizRepository quizRepository;
    private final QuizStateRepository quizStateRepository;
    private final UserRepository userRepository;

    /**
     * userSeq      user_table pk value
     * problem_qtt	int	1~5
     * problem_lv	String	silver/SILVER or gold/GOLD
     */
    @Transactional
    public List<Quiz> pickRandomProblems(int userSeq, int problemCount, String problemLevel) throws Exception {
        checkArgument(1 <= problemCount && problemCount <= 5, "problemCount must be 1 ~ 5");
        checkArgument(problemLevel.toUpperCase().equals(QuizLevel.SILVER.value())
                                || problemLevel.toUpperCase().equals(QuizLevel.GOLD.value()),
                    "problemLevel must be silver/SILVER or gold/GOLD");

        return userRepository.findById(userSeq).map(findUser -> {
            final List<Quiz> notPickedProblems = quizRepository.findAllNotPickedProblems(findUser);
            final List<Quiz> candidateProblems = getCandidateProblems(notPickedProblems, problemLevel);
            final List<Quiz> pickProblems;

            if (candidateProblems.size() <= problemCount) {
                logger.info("candidateProblems.size() <= problemCount");
                pickProblems = candidateProblems;
            } else {
                logger.info("candidateProblems.size() > problemCount");
                pickProblems = getRandomProblems(candidateProblems, problemCount);
            }

            saveNewQuizState(findUser, QuizStateTypeDesc.NOT_SELECTED, pickProblems);

            return pickProblems;

            //FIXME: Exception handling
        }).orElseThrow(() -> new Exception("Hello World"));
    }

    @Transactional
    public void saveNewQuizState(User user, QuizStateTypeDesc quizStateTypeDesc, List<Quiz> pickProblems) {
        checkArgument(user != null, "user must be not null");
        checkArgument(pickProblems != null, "pickProblems must be not null");

        if (pickProblems.isEmpty()) {
            return;
        } else {
            //QuizStateType 얻기
            final QuizStateType quizStateType = quizStateTypeService.findByDesc(quizStateTypeDesc);

            for (Quiz problem : pickProblems) {
                QuizState newQuizState = QuizState.builder()
                        .user(user)
                        .quiz(problem)
                        .quizStateType(quizStateType)
                        .build();

                quizStateRepository.save(newQuizState);
            }
        }
    }

    /**
     * 사용자가 뽑지 않은 문제 List 중에서 problemLevel 에 해당하는 문제들만 반환.
     */
    private List<Quiz> getCandidateProblems(List<Quiz> problems, String problemLevel) {

        checkArgument(problems != null, "problems must be not null");

        if (problems.isEmpty()) {
            return Collections.emptyList();
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
}
