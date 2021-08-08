package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuizStateRepository extends JpaRepository<QuizState, Integer> {

    @Query("select qs.user, count(qs) as cnt from QuizState qs where qs.quizStateType.desc = 'SOLVED' group by qs.user order by cnt desc")
    List<Object[]> findBySolvedQuantity();

    @Query("select qs.user, (sum(case when qs.quizStateType.desc = 'SOLVED' then 1 else 0 end) * 1.0 / sum(case when (qs.quizStateType.desc = 'SOLVED' or qs.quizStateType.desc = 'TC_NOT_PASSED' or qs.quizStateType.desc = 'NOT_SOLVED' or qs.quizStateType.desc = 'TIME_OVER') then 1 else 0 end)) as ratio from QuizState qs group by qs.user order by ratio desc")
    List<Object[]> findByAccuracy();

    /**
     * 현재 풀이중인 문제 목록을 가져온다.
     * state = pns 중에 NOT_SELECTED는 제외
     */
    //FIXME
    @Query("select qs from QuizState qs join fetch qs.user join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 and qs.quizStateType.state like 'pns' and qs.quizStateType.desc not like 'NOT_SELECTED'")
    List<QuizState> findAllSolvingProblems(User user);

    /**
     * User의 QuizState 중 특정 QuizStateTypeDesc에 해당하는 문제 목록을 가져온다.
     * 즉, NOT_SELECTED, NOT_SOLVED, TIME_OVER, TC_NOT_PASSED, SOLVED 중 해당하는 상태(desc)의 문제 목록를 가져온다.
     */
    @Query(value = "SELECT qs FROM QuizState qs join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 and qs.quizStateType.desc = ?2")
    List<QuizState> findAllOfTypeDescProblems(User user, String quizDesc);

    /**
     * User의 QuizState 중 특정 QuizStateTypeState에 해당하는 문제 목록을 가져온다.
     * 즉, npns, pns, ps 중 해당하는 상태(state)의 문제 목록를 가져온다.
     */
    @Query(value = "SELECT qs FROM QuizState qs join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 and qs.quizStateType.state = ?2")
    List<QuizState> findAllOfTypeStateProblems(User user, String quizState);

    @Query(value = "SELECT qs FROM QuizState qs join fetch qs.user join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1")
    List<QuizState> findAllByUser(User user);

    @Query(value = "SELECT qs FROM QuizState qs join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 AND qs.quiz = ?2")
    Optional<QuizState> findByUserAndQuiz(User user, Quiz quiz);

    @Query(value = "SELECT qs FROM QuizState qs join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 and qs.quizStateType.desc = 'NOT_SELECTED'")
    List<QuizState> findAllNotSelectedProblems(User user, Pageable pageable);

    /**
     * For 통계처리
     * '차트로 보기'에 해당하는 로직 처리를 위해 만들어놓은 인터페이스
     * 특정 유저에 해당하는 QuizState 정보를 가져온다.
     * 단, QuizState와 연관되어있는 Quiz의 QuizTag 정보도 전부 긁어오므로 통계 처리 목적으로만 사용한다.
     */
    @Query(value = "SELECT distinct qs, qs.quiz, qs.quiz.quizTags FROM QuizState qs join fetch qs.user join fetch qs.quiz join fetch qs.quizStateType join fetch qs.quiz.quizTags qt join fetch qt.quizTagType WHERE qs.user = ?1")
    List<QuizState> findAll(User user);
}
