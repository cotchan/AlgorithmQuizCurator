package com.yhcy.aqc.model.quiz;

import lombok.AllArgsConstructor;

import static com.google.common.base.Preconditions.checkArgument;

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

    public final String desc() {
        return desc;
    }

    public final String descKor() {
        return descKor;
    }

    public final String state() {
        return state;}

    public static final QuizStateTypeEnum ofCode(final int code) {
        checkArgument(0 <= code && code <= 5, "code value must be 1 ~ 5");

        switch (code) {
            case 0:
                return NOT_PICKED;
            case 1:
                return NOT_SELECTED;
            case 2:
                return TC_NOT_PASSED;
            case 3:
                return NOT_SOLVED;
            case 4:
                return TIME_OVER;
            case 5:
                return SOLVED;
            default:
                //FIXME: Exception 정의 필요
                throw new IllegalArgumentException("");
        }
    }

    public static final int toCode(final String stateTypeDesc) {
        switch (stateTypeDesc) {
            case "NOT_PICKED":
                return 0;
            case "NOT_SELECTED":
                return 1;
            case "TC_NOT_PASSED":
                return 2;
            case "NOT_SOLVED":
                return 3;
            case "TIME_OVER":
                return 4;
            case "SOLVED":
                return 5;
            default:
                //FIXME: Exception 정의 필요
                throw new IllegalArgumentException("");
        }
    }
}
