package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizTagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTagTypeRepository extends JpaRepository<QuizTagType, Integer> {
}
