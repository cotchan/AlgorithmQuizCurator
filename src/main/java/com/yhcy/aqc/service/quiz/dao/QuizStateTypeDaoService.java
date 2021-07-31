package com.yhcy.aqc.service.quiz.dao;

import com.yhcy.aqc.model.quiz.QuizStateType;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.repository.quiz.QuizStateTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizStateTypeDaoService {

    private final QuizStateTypeRepository quizStateTypeRepository;

    public QuizStateType findByDesc(QuizStateTypeEnum quizStateType) {
        return quizStateTypeRepository.findByDesc(quizStateType.desc());
    }

    public List<QuizStateType> findByState(QuizStateTypeEnum quizStateType) {
        return quizStateTypeRepository.findByState(quizStateType.state());
    }

    public QuizStateType findByCode(Integer code) {
        QuizStateTypeEnum quizStateType = QuizStateTypeEnum.ofCode(code);
        return findByDesc(quizStateType);
    }
}
