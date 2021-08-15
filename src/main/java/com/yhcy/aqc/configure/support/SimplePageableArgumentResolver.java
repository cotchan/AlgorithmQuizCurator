package com.yhcy.aqc.configure.support;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class SimplePageableArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${custom.pageable.offset}")
    private int defaultOffset;

    @Value("${custom.pageable.limit}")
    private int defaultLimit;

    private final String offsetParam;
    private final String limitParam;

    public SimplePageableArgumentResolver() {
        this("offset", "limit");
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Pageable.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String offsetString = nativeWebRequest.getParameter(offsetParam);
        final String limitString = nativeWebRequest.getParameter(limitParam);

        int offset = NumberUtils.toInt(offsetString, defaultOffset);
        int limit = NumberUtils.toInt(limitString, defaultLimit);

        if (offset < defaultOffset) {
            offset = defaultOffset;
        }
        if (limit < 1 || limit > defaultLimit) {
            limit = defaultLimit;
        }

        return new PageableImpl(offset, limit);
    }
}