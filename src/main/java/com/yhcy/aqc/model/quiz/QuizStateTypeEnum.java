package com.yhcy.aqc.model.quiz;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuizStateTypeEnum {
    NOT_PICKED("NOT_PICKED","초기화","npns"),
    NOT_SELECTED("NOT_SELECTED","선택하지 않음","pns"),
    TC_NOT_PASSED("TC_NOT_PASSED","일부 테스트케이스 미통과","pns"),
    NOT_SOLVED("NOT_SOLVED","풀이실패","pns"),
    TIME_OVER("TIME_OVER","시간초과","pns"),
    SOLVED("SOLVED","풀이완료","pns");

    private final String desc;
    private final String descKor;
    private final String state;

    public String desc() {
        return desc;
    }

    public String descKor() {
        return descKor;
    }

    public String state() {
        return state;}

    /**
     * 리셋을 했을 때 어떻게 처리?
     * state에
     * 만약 state에 reset이 들어가게 되면 얘가 NPNS (ㅇㅋㅇㅋ)
     */
}
