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
     * fetch join을 사용하면 별도의 ON절을 걸 수 없으므로 nativeQuery로 처리
     */
    @Query(value = "SELECT * FROM quiz left join quiz_state ON quiz.seq = quiz_state.quiz_seq and quiz_state.user_seq = ?1 WHERE quiz_state.seq is null", nativeQuery = true)
    List<Quiz> findAllNotPickedProblems(User user);

    @Query(value = "SELECT q FROM Quiz q WHERE q.number = ?1")
    Optional<Quiz> findByNumber(Integer quizNumber);
}
