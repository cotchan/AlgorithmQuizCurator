package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class QuizPickServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    QuizPickService quizPickService;

    @Test
    @Transactional
    void 문제를_랜덤으로_뽑을_수_있다() {
        final int loopSize = 5;
        final int problemCnt = 3;

        for (int loop = 0; loop < loopSize; ++loop) {
            List<QuizState> quizStates = quizPickService.pickRandomProblems(28, problemCnt);
            for (QuizState quizState : quizStates) {
                logger.info(quizState.toString());
            }
        }
    }

    @Test
    @Transactional
    void problemCnt_갯수만큼_문제를_뽑을_수_있다() {
        int problemCnt = 5;
        List<QuizState> result = quizPickService.pickRandomProblems(28, problemCnt);
        assertThat(result.size(), is(problemCnt));
    }

    @Test
    @Transactional
    void quizState_조회_시점에_Quiz_값은_proxy가_아니다() {
        int problemCnt = 5;
        List<QuizState> result = quizPickService.pickRandomProblems(28, problemCnt);
        for (QuizState q : result) {
            assertThat(q.getQuiz().getNumber(), is(notNullValue()));
            assertThat(q.getQuiz().getTitle(), is(notNullValue()));
            assertThat(q.getQuiz().getRefSiteUrl(), is(notNullValue()));
            assertThat(q.getQuizStateType(), is(notNullValue()));
            assertThat(q.getQuiz().getRefSiteDesc(), is(notNullValue()));
            assertThat(q.getQuiz().getQuizLevel(), is(notNullValue()));
        }
    }

    @Test
    void 문제는_1개에서_5개까지만_뽑을_수_있다_1() throws IllegalArgumentException {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizPickService.pickRandomProblems(25, 10);
        });

        String expectedMessage = "problemCount must be 1 ~ 5";
        String actualMessage = exception.getMessage();
        assertThat(expectedMessage, is(actualMessage));
    }

    @Test
    void 문제는_1개에서_5개까지만_뽑을_수_있다_2() throws IllegalArgumentException {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizPickService.pickRandomProblems(25, 0);
        });

        String expectedMessage = "problemCount must be 1 ~ 5";
        String actualMessage = exception.getMessage();
        assertThat(expectedMessage, is(actualMessage));
    }
}