package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public static UserInfoResponse fromUser(User user) {
        return UserInfoResponse.builder()
                .id(user.getUserId())
                .nickname(user.getNickname())
                .verifyQuestion(user.getVerifyQuestion().getDesc())
                .verifyQuestion(user.getVerifyAnswer())
                .build();
    }
}
