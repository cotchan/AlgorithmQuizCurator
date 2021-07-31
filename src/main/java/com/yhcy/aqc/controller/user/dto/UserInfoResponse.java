package com.yhcy.aqc.controller.user.dto;

import com.yhcy.aqc.model.user.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private final String id;
    private final String nickname;
    private final String verifyQuestion;
    private final String verifyAnswer;

    public UserInfoResponse(User user) {
        this.id = user.getUserId();
        this.nickname = user.getNickname();
        this.verifyQuestion = user.getVerifyQuestion().getDesc();
        this.verifyAnswer = user.getVerifyAnswer();
    }

    public static UserInfoResponse fromUser(User user) {
        return new UserInfoResponse(user);
    }
}
