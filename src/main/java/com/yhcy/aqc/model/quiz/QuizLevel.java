package com.yhcy.aqc.model.quiz;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum QuizLevel {

    SILVER("SILVER"),
    GOLD("GOLD");

    private final String value;

    public final String value() {
        return value;
    }

}
