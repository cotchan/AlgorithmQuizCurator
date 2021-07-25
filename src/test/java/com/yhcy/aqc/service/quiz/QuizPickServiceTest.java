package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Thread.sleep;


@SpringBootTest
class QuizPickServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    QuizPickService quizPickService;

    @Test
    @Transactional
//    @Rollback(false)
    void 문제를_랜덤으로_뽑을_수_있다() throws Exception {

        for (int loop = 0; loop < 30; ++loop) {
            List<QuizState> quizStates = quizPickService.pickRandomProblems(28, 5);

            logger.debug("====================QUIZ_STATE====================");
            for (QuizState quizState : quizStates) {
                logger.debug(quizState.toString());
            }
            logger.debug("====================QUIZ_STATE====================");

            sleep(3000);
        }
    }

    @Test
    void 골드나_실버를_제외한_다른_문자_입력하면_예외() throws Exception {
        quizPickService.pickRandomProblems(25, 5);
    }

    @Test
    void 문제는_1개에서_5개까지만_뽑을_수_있다() throws Exception {
        quizPickService.pickRandomProblems(25, 10);
    }

}