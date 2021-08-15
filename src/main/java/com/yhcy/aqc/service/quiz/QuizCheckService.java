package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.service.quiz.dao.QuizDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizLogDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateTypeDaoService;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class QuizCheckService {

    private final QuizDaoService quizDaoService;
    private final QuizLogDaoService quizLogDaoService;
    private final QuizStateDaoService quizStateService;
    private final QuizStateTypeDaoService quizStateTypeDaoService;
    private final UserDaoService userDaoService;

    @Transactional
    public List<QuizState> update(final int userSeq, final List<QuizStateForUpdate> updateRequests) {
        checkArgument(updateRequests != null, "updateRequests must be not null");

        final User user = userDaoService.findById(userSeq);

        List<QuizState> results = new LinkedList<>();

        for (QuizStateForUpdate updateRequest : updateRequests) {
            final int quizNumber = updateRequest.getQuizNumber();
            final int quizStateTypeCode = updateRequest.getQuizStateTypeCode();

            final Quiz quiz = quizDaoService.findByNumber(quizNumber);
            final QuizState quizState = quizStateService.findByUserAndQuiz(user, quiz);
            final QuizStateType newQuizStateType = quizStateTypeDaoService.findByCode(quizStateTypeCode);
            quizState.updateQuizStateType(newQuizStateType);
            quizLogDaoService.save(user, newQuizStateType, quiz);
            results.add(quizState);
        }

        return results;
    }

    public List<QuizState> getNotSelectedProblems(final int userSeq, final int offset, final int limit) {
        final User user = userDaoService.findById(userSeq);
        return quizStateService.getNotSelectedProblems(user, offset, limit);
    }
}
