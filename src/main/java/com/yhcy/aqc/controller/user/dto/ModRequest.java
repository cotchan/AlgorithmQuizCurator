package com.yhcy.aqc.controller.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ModRequest {
    private String id;
    private String pw;
    private String pwConfirm;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;
}
