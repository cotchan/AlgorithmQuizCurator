package com.yhcy.aqc.error;

public class NotFoundException extends ServiceRuntimeException {

    public NotFoundException(Class<?> cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    public NotFoundException(String targetName, Object... values) {
        super();
    }
}