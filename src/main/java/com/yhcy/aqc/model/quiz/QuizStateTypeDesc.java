package com.yhcy.aqc.model.quiz;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuizStateTypeDesc {
    NOT_SELECTED("NOT_SELECTED"),
    TC_NOT_PASSED("TC_NOT_PASSED"),
    NOT_SOLVED("NOT_SOLVED"),
    TIME_OVER("TIME_OVER"),
    SOLVED("SOLVED");

    private final String val;

    public String value() {
        return val;
    }
}
