package com.yhcy.aqc.service.user.dao;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.user.VerifyQuestion;
import com.yhcy.aqc.repository.user.VerifyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;

@RequiredArgsConstructor
@Service
public class VerifyQuestionDaoService {

    private final VerifyQuestionRepository verifyQuestionRepository;

    public VerifyQuestion findBySeq(final int seq) {
        checkArgument(seq > 0, "seq must be positive number");
        return verifyQuestionRepository.findBySeq(seq).orElseThrow(() -> new NotFoundException(VerifyQuestion.class, seq));
    }

    public VerifyQuestion findBySeq(final String seqStr) {
        int seq;
        try {
            seq = Integer.parseInt(seqStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid verify question index ["+ seqStr+"]");
        }
        return verifyQuestionRepository.findBySeq(seq).orElseThrow(() -> new NotFoundException(VerifyQuestion.class, seq));
    }
}
