package com.yhcy.aqc.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private String id;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;

    @Builder
    public UserResponse(String id, String nickname, String verifyQuestion, String verifyAnswer) {
        this.id = id;
        this.nickname = nickname;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
    }
}
