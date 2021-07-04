package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizStateTypeRepository extends JpaRepository<QuizStateType, Integer> {
}
