package com.yhcy.aqc.controller.quiz.pick;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizPickRequest {
    private String problemCnt;

    public QuizPickRequest(String problemCnt) {
        this.problemCnt = problemCnt;
    }
}
