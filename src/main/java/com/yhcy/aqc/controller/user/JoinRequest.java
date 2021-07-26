package com.yhcy.aqc.controller.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JoinRequest {
    private final String id;
    private final String pw;
    private final String pwConfirm;
    private final String nickname;
    private final String verifyQuestion;
    private final String verifyAnswer;

    public JoinRequest(String id, String pw, String pwConfirm, String nickname,
                       String verifyQuestion, String verifyAnswer) {
        this.id = id.trim();
        this.pw = pw.trim();
        this.pwConfirm = pwConfirm.trim();
        this.nickname = nickname.trim();
        this.verifyQuestion = verifyQuestion.trim();
        this.verifyAnswer = verifyAnswer.trim();
    }
}
