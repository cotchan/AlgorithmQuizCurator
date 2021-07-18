package com.yhcy.aqc.controller.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

/**
 * 에러 응답 포맷입니다.
 *  에러에 해당하는 HttpStatus Code와 Message를 담고 있습니다.
 *  에러 메시지는 아래와 같이 두 가지 케이스로 생성됩니다.
 *      1. Exception에 정의된 메시지를 그대로 사용
 *      2. 커스터마이징 하여 사용
 */
public class ApiError {

    private final String message;

    private final int status;

    ApiError(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(), status);
    }

    ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("message", message)
                .append("status", status)
                .toString();
    }

}
