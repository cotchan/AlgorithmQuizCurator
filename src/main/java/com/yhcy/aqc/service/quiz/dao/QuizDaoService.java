package com.yhcy.aqc.service.quiz.dao;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class QuizDaoService {

    private final QuizRepository quizRepository;

    public Quiz findByNumber(final int quizNumber) {
        return quizRepository.findByNumber(quizNumber).orElseThrow(() -> new NotFoundException(Quiz.class, quizNumber));
    }

    public List<Quiz> findAllNotPickedProblems(final User user) {
        checkArgument(user != null, "user must be not null");
        return quizRepository.findAllNotPickedProblems(user);
    }
}
