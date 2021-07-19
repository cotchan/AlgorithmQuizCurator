package com.yhcy.aqc.controller.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
    private String id;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;

    @Builder
    public UserInfoResponse(String id, String nickname, String verifyQuestion, String verifyAnswer) {
        this.id = id;
        this.nickname = nickname;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
    }
}
