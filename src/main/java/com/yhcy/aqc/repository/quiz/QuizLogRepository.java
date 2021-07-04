package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizLogRepository extends JpaRepository<QuizLog, Integer> {
}
