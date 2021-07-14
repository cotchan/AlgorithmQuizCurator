package com.yhcy.aqc.service.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {
    private String id;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;

    @Builder
    public UserDTO(String id, String nickname, String verifyQuestion, String verifyAnswer) {
        this.id = id;
        this.nickname = nickname;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
    }
}
