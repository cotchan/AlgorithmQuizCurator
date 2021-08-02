package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.service.quiz.dao.QuizDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateDaoService;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuizCheckServiceTest {

    @Autowired
    QuizCheckService quizCheckService;

    @Autowired
    QuizStateDaoService quizStateService;

    @Autowired
    UserDaoService userService;

    @Autowired
    QuizDaoService quizService;

    @Test
    @Transactional
    public void quizState_상태를_갱신할_수_있다() {

        //given
        final int validQuizNumber = 14889;
        final User user = userService.findById(28);
        final Quiz quiz = quizService.findByNumber(validQuizNumber);
        final QuizState before = quizStateService.findByUserAndQuiz(user, quiz);

        final int beforeTypeCode = QuizStateTypeEnum.toCode(before.getQuizStateType().getDesc());

        int validQuizStateTypeCode = -1;
        for (int code = 0; code < 6; ++code) {
            if (code != beforeTypeCode) {
                validQuizStateTypeCode = code;
                break;
            }
        }

        List<QuizStateForUpdate> params = new ArrayList<>();
        QuizStateForUpdate quizStateForUpdate = new QuizStateForUpdate(validQuizNumber, validQuizStateTypeCode);
        params.add(quizStateForUpdate);

        //when
        quizCheckService.update(28, params);
    }

    @Test
    public void 올바르지_않은_quizNumber는_갱신할_수_없다() {

        //given
        final int invalidQuizNumber = -1;
        final int validQuizStateTypeCode = 2;

        List<QuizStateForUpdate> params = new ArrayList<>();
        QuizStateForUpdate quizStateForUpdate = new QuizStateForUpdate(invalidQuizNumber, validQuizStateTypeCode);
        params.add(quizStateForUpdate);

        //when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            quizCheckService.update(28, params);
        });

        //then
        String expectedMessage = "Could not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void 올바르지_않은_quizStateTypeCode는_갱신할_수_없다() {

        //given
        final int validQuizNumber = 14889;
        final int invalidQuizStateTypeCode = -1;
        List<QuizStateForUpdate> params = new ArrayList<>();
        QuizStateForUpdate quizStateForUpdate = new QuizStateForUpdate(validQuizNumber, invalidQuizStateTypeCode);
        params.add(quizStateForUpdate);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            quizCheckService.update(28, params);
        });

        //then
        String expectedMessage = "code value must be 0 ~ 5";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}