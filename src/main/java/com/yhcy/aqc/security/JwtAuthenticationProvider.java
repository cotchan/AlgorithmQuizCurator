package com.yhcy.aqc.security;

import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static org.springframework.util.ClassUtils.isAssignable;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        /**
         * 이 인증 주체에 대한 처리를 할 수 있다고 했기 때문에 이 인증 주체 타입(JwtAuthenticationToken)으로 타입 캐스팅을 합니다.
         * 이 인증 주체는 '로그인 전'을 의미하는 인증주체
         */
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        //그리고 나서 실제 UserSerivce를 이용해서 로그인 처리를 합니다.
        return processUserAuthentication(authenticationToken.authenticationRequest());
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            //여기에 principal 부분은 email, credentials 부분은 비밀번호입니다.
            User user = userService.login(request.getPrincipal(), request.getCredentials());

            /**
             * 인증 주체를 새로 만들고 있습니다.
             * 이 인증 주체는 '로그인 후'를 의미하는 인증주체
             */
            JwtAuthenticationToken authenticated =
                    // 응답용 Authentication 인스턴스를 생성한다.
                    // 지금의 JwtAuthenticationToken.principal 부분에는 JwtAuthentication 인스턴스가 set 된다. (아예 새로운 타입) 그래도 문제가 없는 이유는 Object타입이기 때문입니다.
                    // 맨 처음에는(로그인 완료 전) JwtAuthenticationToken.principal 부분은 Email 인스턴스가 set 되어 있었다.
                    //로그인 전과 후의 인증 주체의 타입이 아예 달라지는 것을 나타냅니다.
                    new JwtAuthenticationToken(new JwtAuthentication(user.getSeq(), user.getUserId(), user.getNickName()), null, createAuthorityList(Role.USER.value()));
            // JWT 값을 생성한다.
            // 권한은 ROLE_USER 를 부여한다.
            // jwt 토큰에 권한을 부여하는 것입니다. 유저라는 권한을 부여
            //그래서 JWT를 만들고 이걸 최종적으로 AuthenticationResult라는 타입에 담아서 얘를 리턴해줍니다.
            // 그러면 로그인이 완료된 것입니다.

            //토큰을 만듭니다.
            String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});

            //이 토큰을 detail이라는 곳에 AuthenticationResult 타입으로 담았습니다. 그리고 authenticated를 리턴
            authenticated.setDetails(new AuthenticationResult(apiToken, user));
            return authenticated;
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
}
