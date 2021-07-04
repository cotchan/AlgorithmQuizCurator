package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizStateRepository extends JpaRepository<QuizState, Integer> {
}
