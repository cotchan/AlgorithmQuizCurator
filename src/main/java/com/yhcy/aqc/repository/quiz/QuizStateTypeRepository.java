package com.yhcy.aqc.repository.quiz;

import com.yhcy.aqc.model.quiz.QuizStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizStateTypeRepository extends JpaRepository<QuizStateType, Integer> {

    QuizStateType findByDesc(String desc);

    List<QuizStateType> findByState(String state);

    @Query("select qst from QuizStateType qst where qst.state = 'pns' or qst.state = 'ps' order by qst.seq")
    List<QuizStateType> findAllStates();
}
