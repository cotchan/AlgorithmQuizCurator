package com.yhcy.aqc.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ModRequest {
    private String id;
    private String pw;
    private String pwConfirm;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;

    public ModRequest(String id, String pw, String pwConfirm, String nickname,
                      String verifyQuestion, String verifyAnswer) {
        this.id = id.trim();
        this.pw = pw.trim();
        this.pwConfirm = pwConfirm.trim();
        this.nickname = nickname.trim();
        this.verifyQuestion = verifyQuestion.trim();
        this.verifyAnswer = verifyAnswer.trim();
    }
}
