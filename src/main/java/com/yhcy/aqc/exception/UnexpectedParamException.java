package com.yhcy.aqc.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnexpectedParamException extends RuntimeException {

    private String message;

    public UnexpectedParamException(String message) {
        super(message);
        this.message = message;
    }
}
