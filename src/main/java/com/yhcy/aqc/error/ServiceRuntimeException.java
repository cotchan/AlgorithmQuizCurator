package com.yhcy.aqc.error;

import lombok.Getter;

@Getter
public abstract class ServiceRuntimeException extends RuntimeException {

    private String messageKey;

    private String detailKey;

    private Object[] params;

    public ServiceRuntimeException(String messageKey, String detailKey, Object[] params) {
        this.messageKey = messageKey;
        this.detailKey = detailKey;
        this.params = params;
    }
}
