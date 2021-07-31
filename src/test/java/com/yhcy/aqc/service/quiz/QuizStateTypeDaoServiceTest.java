package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.service.quiz.dao.QuizStateTypeDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class QuizStateTypeDaoServiceTest {

    @Autowired
    QuizStateTypeDaoService quizStateTypeDaoService;

    @Test
    void findByDesc_테스트() {
        QuizStateType result1 = quizStateTypeDaoService.findByDesc(QuizStateTypeEnum.NOT_SELECTED);
        assertThat(result1.getDesc(), is("NOT_SELECTED"));
        assertThat(result1.getState(), is("pns"));

        QuizStateType result2 = quizStateTypeDaoService.findByDesc(QuizStateTypeEnum.SOLVED);

        assertThat(result2.getDesc(), is("SOLVED"));
        assertThat(result2.getState(), is("ps"));
    }
}