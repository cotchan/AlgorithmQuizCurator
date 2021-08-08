package com.yhcy.aqc.controller.user.dto;

import lombok.Getter;

@Getter
public class VerifyQuestionResponse {
    private int code;
    private String desc;

    public VerifyQuestionResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
