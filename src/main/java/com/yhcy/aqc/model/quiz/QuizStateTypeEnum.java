package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.error.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        checkArgument(0 <= code && code <= 5, "code value must be 0 ~ 5");

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
                throw new NotFoundException(QuizStateTypeEnum.class, code);
        }
    }

    public static final int toCode(final String stateTypeDesc) {
        if (NOT_PICKED.desc().equals(stateTypeDesc)) {
            return 0;
        } else if (NOT_SELECTED.desc().equals(stateTypeDesc)) {
            return 1;
        } else if (TC_NOT_PASSED.desc().equals(stateTypeDesc)) {
            return 2;
        } else if (NOT_SOLVED.desc().equals(stateTypeDesc)) {
            return 3;
        } else if (TIME_OVER.desc().equals(stateTypeDesc)) {
            return 4;
        } else if (SOLVED.desc().equals(stateTypeDesc)) {
            return 5;
        } else {
            throw new NotFoundException(QuizStateTypeEnum.class, stateTypeDesc);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("desc", desc)
                .append("desc_kor", descKor)
                .append("state", state)
                .toString();
    }
}
