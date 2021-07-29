package com.yhcy.aqc.error;

import com.yhcy.aqc.util.MessageUtils;

public class UnauthorizedException extends ServiceRuntimeException {

    public static final String MESSAGE_KEY = "error.auth";

    public static final String MESSAGE_DETAIL = "error.auth.details";

    public UnauthorizedException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getMessage(getMessageKey());
    }
}