package com.yhcy.aqc.model.quiz;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuizStateTypeEnum {
    NOT_PICKED("NOT_PICKED","npns"),
    NOT_SELECTED("NOT_SELECTED","pns"),
    TC_NOT_PASSED("TC_NOT_PASSED","pns"),
    NOT_SOLVED("NOT_SOLVED","pns"),
    TIME_OVER("TIME_OVER","pns"),
    SOLVED("SOLVED","pns");

    private final String desc;
    private final String state;

    public String desc() {
        return desc;
    }

    public String state() {
        return state;}

    /**
     * 리셋을 했을 때 어떻게 처리?
     * state에
     * 만약 state에 reset이 들어가게 되면 얘가 NPNS (ㅇㅋㅇㅋ)
     */
}
