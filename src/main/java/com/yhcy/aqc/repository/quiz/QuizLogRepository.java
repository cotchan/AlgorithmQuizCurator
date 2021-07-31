package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizLogRepository extends JpaRepository<QuizLog, Integer> {

    @Query(value = "SELECT ql FROM QuizLog ql join fetch ql.user join fetch ql.quiz join fetch ql.quizStateType where ql.user = ?1")
    List<QuizLog> findAllByUser(User user);
}
