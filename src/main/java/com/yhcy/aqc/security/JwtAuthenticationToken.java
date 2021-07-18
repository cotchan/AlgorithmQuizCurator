package com.yhcy.aqc.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 인증 주체를 나타낸다.
     * 타입이 Object인 이유는 로그인 전과 로그인 후의 타입이 다르기 때문이다.
     * 로그인 전에는 String 타입이고, 로그인 후에는 {@link JwtAuthentication} 타입
     * 단, 인증이 정상적으로 완료된 경우에만 사용
     */
    private final Object principal;

    //비밀번호
    private String credentials;

    public JwtAuthenticationToken(String principal, String credentials) {
        super(null);
        super.setAuthenticated(false);  //'아직 인증이 완료되지 않았다'는 의미

        this.principal = principal;
        this.credentials = credentials;
    }

    protected JwtAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    protected AuthenticationRequest authenticationRequest() {
        return new AuthenticationRequest(String.valueOf(principal), credentials);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principal", principal)
                .append("credentials", "[PROTECTED]")
                .toString();
    }


}
