package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizStateRepository extends JpaRepository<QuizState, Integer> {

    //TODO : qs.quizStateType.desc -> qs.quizStateType.state 로 바꾸기
    @Query("select qs.user, count(qs) as cnt from QuizState qs where qs.quizStateType.desc = 'SOLVED' group by qs.user order by cnt desc")
    List<Object[]> findBySolvedQuantity();

    //TODO : qs.quizStateType.desc -> qs.quizStateType.state 로 바꾸기
    //인텔리제이 코드 에디터에서는 빨간줄이 생기나 실제 동작에는 문제없음
    @Query("select qs.user, (sum(case when qs.quizStateType.desc = 'SOLVED' then 1 else 0 end) * 1.0 / sum(case when (qs.quizStateType.desc = 'SOLVED' or qs.quizStateType.desc = 'TC_NOT_PASSED' or qs.quizStateType.desc = 'NOT_SOLVED' or qs.quizStateType.desc = 'TIME_OVER') then 1 else 0 end)) as ratio from QuizState qs group by qs.user order by ratio desc")
    List<Object[]> findByAccuracy();
}
