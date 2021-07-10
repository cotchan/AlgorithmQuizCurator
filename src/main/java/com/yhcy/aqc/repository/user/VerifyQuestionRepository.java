package com.yhcy.aqc.repository.user;

import com.yhcy.aqc.model.user.VerifyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyQuestionRepository extends JpaRepository<VerifyQuestion, Integer> {

    VerifyQuestion findBySeq(Integer seq);
}
