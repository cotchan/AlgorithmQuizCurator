package com.yhcy.aqc.model.quiz;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
public enum QuizLevel {

    SILVER("Silver"),
    GOLD("Gold");

    private final String value;

    QuizLevel(String value) {
        this.value = value;
    }
}
