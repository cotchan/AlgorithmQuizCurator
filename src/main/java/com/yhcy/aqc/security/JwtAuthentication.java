package com.yhcy.aqc.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 인증된 사용자를 의미합니다.
 */
@RequiredArgsConstructor
public class JwtAuthentication {

    public final Integer seq;

    public final String userId;

    public final String nickName;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("userId", userId)
                .append("nickName", nickName)
                .toString();
    }
}
