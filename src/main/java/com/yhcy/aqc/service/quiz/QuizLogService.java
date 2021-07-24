package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizLogRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizLogService {

    private final QuizLogRepository logRepo;
    private final UserRepository userRepo;

    public List<QuizLog> getQuizLogsByUserId(String userId) {
        Optional<User> user = userRepo.findByUserId(userId);
        List<QuizLog> logs = logRepo.findAllByUser(user.get());
        Collections.reverse(logs);
        return logs;
    }

}
