package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.error.UnexpectedParamException;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizLogRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizLogService {

    private final QuizLogRepository logRepo;
    private final UserRepository userRepo;

    public List<QuizLog> getQuizLogsByUserId(String userId, int pageSize, int pageNo) throws Exception {
        if (pageSize < 1 || pageNo < 1)
            throw new UnexpectedParamException("requested param must be positive number");
        Optional<User> user = userRepo.findByUserId(userId);
        List<QuizLog> logs = logRepo.findAllByUser(user.get());
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

}
