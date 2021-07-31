package com.yhcy.aqc.error;

import com.yhcy.aqc.util.MessageUtils;

public class UnexpectedParamException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.unexpected-params";
    static final String MESSAGE_DETAIL = "error.unexpected-params.details";

    public UnexpectedParamException(String message) {
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
