package com.yhcy.aqc.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(
                        new JwtAuthentication(customUser.id(), customUser.userId(), customUser.nickName()),
                        null,
                        createAuthorityList(customUser.role())
                );

        context.setAuthentication(authentication);
        return context;
    }
}
