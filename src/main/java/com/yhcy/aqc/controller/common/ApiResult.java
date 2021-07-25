package com.yhcy.aqc.controller.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 응답 포맷입니다. 모든 응답은 ApiResult 스펙을 따릅니다.
 * 모든 응답 형태를 받을 수 있도록 <T> 제네릭을 적용합니다.
 * 성공 응답 시
 *  boolean isSuccess => true
 *  T response => 실제 데이터가 담깁니다.
 *  ApiError => null
 *
 * 실패 응답 시
 *  boolean isSuccess => false
 *  T response => null
 *  ApiError => new ApiError() 실제 에러값이 답깁니다.
 *
 */
public class ApiResult<T> {

    private final boolean success;

    private final T response;

    private final ApiError error;

    private final String timeStamp;

    private ApiResult(boolean success, T response, ApiError error) {
        this.success = success;
        this.response = response;
        this.error = error;
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static <T> ApiResult<T> OK(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> ERROR(String errorMessage, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(errorMessage, status));
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiError getError() {
        return error;
    }

    public T getResponse() {
        return response;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("success", success)
                .append("response", response)
                .append("error", error)
                .toString();
    }

}
