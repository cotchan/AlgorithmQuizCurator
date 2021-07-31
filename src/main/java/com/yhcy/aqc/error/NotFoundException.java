package com.yhcy.aqc.error;

import com.yhcy.aqc.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notfound";
    static final String MESSAGE_DETAILS = "error.notfound.details";

    public NotFoundException(Class<?> cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    //StringUtils.join을 사용하면 Object의 toString 값을 리턴해줌 -> 오버라이딩하면 됨
    public NotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
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