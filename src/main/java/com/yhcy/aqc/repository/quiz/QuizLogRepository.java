package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizLogRepository extends JpaRepository<QuizLog, Integer> {

    List<QuizLog> findAllByUser(User user);
}
