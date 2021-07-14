package com.yhcy.aqc.service.user;

import lombok.Getter;

@Getter
public class UnexpectedParamException extends RuntimeException {

    private String message;

    public UnexpectedParamException(String message) {
        super(message);
        this.message = message;
    }
}
