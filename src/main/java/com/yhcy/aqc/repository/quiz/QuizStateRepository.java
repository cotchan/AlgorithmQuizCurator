package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizStateRepository extends JpaRepository<QuizState, Integer> {

    @Query(value = "SELECT qs FROM QuizState qs WHERE qs.user = ?1 and qs.quizStateType.state = ?2")
    List<QuizState> findAllOfTypeStateProblems(User user, String quizState);
}
