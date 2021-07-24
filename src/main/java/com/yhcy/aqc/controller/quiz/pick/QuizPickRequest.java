package com.yhcy.aqc.controller.quiz.pick;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class QuizPickRequest {

    private final String problemCnt;
    private final String problemLevel;

}
