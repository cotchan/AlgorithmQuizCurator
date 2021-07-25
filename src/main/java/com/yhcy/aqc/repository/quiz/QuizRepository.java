package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    /**
     * 유저가 아직 뽑지 않은 문제 List를 조회한다.
     * JPA에서 참조 당하는 쪽을 LEFT TABLE로 하는 LEFT JOIN을 지원하지 않으므로 RIGHT JOIN으로 작성
     */
    @Query(value = "SELECT qs.quiz FROM QuizState qs RIGHT JOIN qs.quiz q ON qs.user = ?1 WHERE qs.seq is null")
    List<Quiz> findAllNotPickedProblems(User user);

    @Query(value = "SELECT q FROM Quiz q WHERE q.number = ?1")
    Optional<Quiz> findByNumber(Integer quizNumber);
}
