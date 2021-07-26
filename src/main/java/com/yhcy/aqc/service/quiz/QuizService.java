package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizRepository;
import com.yhcy.aqc.repository.quiz.QuizStateRepository;
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
public class QuizService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final QuizStateRepository quizStateRepository;
    private final QuizStateTypeService quizStateTypeService;
    private final QuizRepository quizRepository;

    @Transactional
    public List<QuizState> update(final int userSeq, final List<QuizStateForUpdate> updateRequests) throws Exception {
        checkArgument(updateRequests != null, "updateRequests must be not null");

        final User user = userRepository.findById(userSeq).orElseThrow(() -> new Exception("NOT_FOUND_EXCEPTION"));

        List<QuizState> results = new LinkedList<>();

        for (QuizStateForUpdate updateRequest : updateRequests) {
            final int quizNumber = updateRequest.getQuizNumber();
            final int quizStateTypeCode = updateRequest.getQuizStateTypeCode();

            final Quiz quiz = quizRepository.findByNumber(quizNumber).orElseThrow(() -> new Exception("NOT_FOUND_EXCEPTION"));
            final QuizState quizState = quizStateRepository.findByUserAndQuiz(user, quiz).orElseThrow(() -> new Exception("NOT_FOUND_EXCEPTION"));
            final QuizStateType newQuizStateType = quizStateTypeService.findByCode(quizStateTypeCode);
            quizState.updateQuizStateType(newQuizStateType);
            results.add(quizState);
        }

        return results;
    }
}
