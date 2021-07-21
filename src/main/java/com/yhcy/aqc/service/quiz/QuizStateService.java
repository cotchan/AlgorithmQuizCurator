package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.repository.quiz.QuizRepository;
import com.yhcy.aqc.repository.quiz.QuizStateRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizStateService {

    private final QuizRepository quizRepo;
    private final QuizStateRepository stateRepo;
    private final UserRepository userRepo;


    public List<QuizState> getQuizByStateAndUserId(List<String> stateTypes, String userId) {

        return null;
    }
}
