package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
public class QuizCheckService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final QuizService quizService;
    private final QuizLogService quizLogService;
    private final QuizStateService quizStateService;
    private final QuizStateTypeService quizStateTypeService;

    @Transactional
    public List<QuizState> update(final int userSeq, final List<QuizStateForUpdate> updateRequests) {
        checkArgument(updateRequests != null, "updateRequests must be not null");

        final User user = userRepository.findById(userSeq).orElseThrow(() -> new NotFoundException(User.class, userSeq));

        List<QuizState> results = new LinkedList<>();

        for (QuizStateForUpdate updateRequest : updateRequests) {
            final int quizNumber = updateRequest.getQuizNumber();
            final int quizStateTypeCode = updateRequest.getQuizStateTypeCode();

            final Quiz quiz = quizService.findByNumber(quizNumber);
            final QuizState quizState = quizStateService.findByUserAndQuiz(user, quiz);
            final QuizStateType newQuizStateType = quizStateTypeService.findByCode(quizStateTypeCode);
            quizState.updateQuizStateType(newQuizStateType);
            quizLogService.save(user, newQuizStateType, quiz);
            results.add(quizState);
        }

        return results;
    }
}
