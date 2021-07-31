package com.yhcy.aqc.service.quiz.dao;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizLogRepository;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class QuizLogDaoService {

    private final UserDaoService userDaoService;
    private final QuizLogRepository logRepo;

    public List<QuizLog> getQuizLogsByUserId(final String userId, final int pageSize, final int pageNo) {
        checkArgument(pageSize >= 1 || pageNo >= 1, "requested param must be positive number");

        User user = userDaoService.findByUserId(userId);
        List<QuizLog> logs = logRepo.findAllByUser(user);
        Collections.reverse(logs);

        List<QuizLog> result = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            try {
                result.add(logs.get(pageSize * (pageNo - 1) + i));
            } catch (Exception e) {
                break;
            }
        }
        return result;
    }

    @Transactional
    public QuizLog save(final User user, final QuizStateType quizStateType, final Quiz quiz) {
        checkArgument(user != null, "user must be not null");
        checkArgument(quizStateType != null, "quizStateType must be not null");
        checkArgument(quiz != null, "quiz must be not null");

        QuizLog quizLog = QuizLog.builder()
                .user(user)
                .quiz(quiz)
                .quizStateType(quizStateType)
                .build();
        return logRepo.save(quizLog);
    }
}
