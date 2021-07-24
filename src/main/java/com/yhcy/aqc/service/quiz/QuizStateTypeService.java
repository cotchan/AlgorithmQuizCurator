package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeDesc;
import com.yhcy.aqc.repository.quiz.QuizStateTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizStateTypeService {

    private final QuizStateTypeRepository quizStateTypeRepository;

    QuizStateType findByDesc(QuizStateTypeDesc desc) {
        return quizStateTypeRepository.findByDesc(desc.value());
    }
}
