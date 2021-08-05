package com.yhcy.aqc.controller.quiz.dto;

import lombok.Getter;

@Getter
public class QuizStateTypeResponse {
    private int stateCode;
    private String descKor;

    public QuizStateTypeResponse(int stateCode, String descKor) {
        this.stateCode = stateCode;
        this.descKor = descKor;
    }
}
