package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTagRepository extends JpaRepository<QuizTag, Integer> {
}
