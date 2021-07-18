package com.yhcy.aqc.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    int id() default 23;

    String userId() default "zoroKevin";

    String nickName() default "test001";

    String role() default "ROLE_USER";
}