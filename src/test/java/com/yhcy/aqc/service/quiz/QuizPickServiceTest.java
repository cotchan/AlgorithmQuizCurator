package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class QuizPickServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    QuizPickService quizPickService;

    @Test
    @Transactional
    @Rollback(false)
    void 문제를_랜덤으로_뽑을_수_있다() throws Exception {

        for (int loop = 0; loop < 35; ++loop) {
            List<QuizState> quizStates = quizPickService.pickRandomProblems(28, 5);

            logger.info("====================QUIZ_STATE====================");
            for (QuizState quizState : quizStates) {
                logger.info(quizState.toString());
            }
            logger.info("====================QUIZ_STATE====================");

            sleep(3000);
        }
    }

    @Test
    void 문제는_1개에서_5개까지만_뽑을_수_있다() throws Exception {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizPickService.pickRandomProblems(25, 10);
        });

        String expectedMessage = "problemCount must be 1 ~ 5";
        String actualMessage = exception.getMessage();
        assertThat(expectedMessage, is(actualMessage));
    }
}