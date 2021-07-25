package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.repository.quiz.QuizStateTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizStateTypeService {

    private final QuizStateTypeRepository quizStateTypeRepository;

    QuizStateType findByDesc(QuizStateTypeEnum quizStateType) {
        return quizStateTypeRepository.findByDesc(quizStateType.desc());
    }

    List<QuizStateType> findByState(QuizStateTypeEnum quizStateType) {
        return quizStateTypeRepository.findByState(quizStateType.state());
    }

    QuizStateType findByCode(Integer code) {
        QuizStateTypeEnum quizStateType = QuizStateTypeEnum.ofCode(code);
        return findByDesc(quizStateType);
    }
}
