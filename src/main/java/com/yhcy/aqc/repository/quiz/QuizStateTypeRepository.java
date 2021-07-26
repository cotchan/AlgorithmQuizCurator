package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizStateTypeRepository extends JpaRepository<QuizStateType, Integer> {

    QuizStateType findByDesc(String desc);

    List<QuizStateType> findByState(String state);
}
