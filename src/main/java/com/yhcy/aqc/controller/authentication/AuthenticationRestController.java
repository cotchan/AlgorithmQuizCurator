package com.yhcy.aqc.controller.authentication;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.error.UnauthorizedException;
import com.yhcy.aqc.security.AuthenticationRequest;
import com.yhcy.aqc.security.AuthenticationResult;
import com.yhcy.aqc.security.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yhcy.aqc.controller.common.ApiResult.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/login")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ApiResult<AuthenticationResultDto> authentication(@RequestBody AuthenticationRequest authRequest)
            throws UnauthorizedException {
        try {

            //인증 주체 만들기
            JwtAuthenticationToken authToken = new JwtAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());

            //만든 인증 주체를 AuthenticationManager에게 위임합니다.
            //AuthenticationManager는 인터페이스이고 실제 구현은 ProviderManager입니다.
            //ProviderManager는 AuthenticationProvider를 list로 가지고 있습니다.
            //Manager는 AuthenticationProvider를 List를 통해 실제 인증을 합니다. 즉, AuthenticationProvider를 사용해서 인증을 한다는 의미입니다.

            //AuthenticationProvider 하나를 선택해서 실제 로그인 요청을 합니다.
            //이걸 선택하는 방법은 AuthenticationProvider::supports 메서드가 true를 리턴하면, 이 provider가 바로 로그인을 처리할 provider가 됩니다.
            //그러면 이 provider가 처리를 할 거구나 라는 게 확정이 되고, provider의 authenticate 메서드를 호출하게 됩니다.
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return OK(
                    new AuthenticationResultDto((AuthenticationResult) authentication.getDetails())
            );

        } catch (AuthenticationException e) {
            //TODO: AuthenticationException 처리 필요
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
