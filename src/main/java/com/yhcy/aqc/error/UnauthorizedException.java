package com.yhcy.aqc.error;

public class UnauthorizedException extends ServiceRuntimeException {

    public UnauthorizedException(String message) {
        super();
    }

    @Override
    public String getMessage() {
        return "getMessage";
    }
}