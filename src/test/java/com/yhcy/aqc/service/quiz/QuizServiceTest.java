package com.yhcy.aqc.service.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class QuizServiceTest {


    @Autowired
    QuizService quizService;

    @Test
    void 문제를_랜덤으로_뽑을_수_있다() throws Exception {
        quizService.pickRandomProblems(28, 5, "silVer");
    }

    @Test
    void 골드나_실버를_제외한_다른_문자_입력하면_예외() throws Exception {
        quizService.pickRandomProblems(25, 5, "SHILVER");
    }

    @Test
    void 문제는_1개에서_5개까지만_뽑을_수_있다() throws Exception {
        quizService.pickRandomProblems(25, 10, "GOLD");
    }
}