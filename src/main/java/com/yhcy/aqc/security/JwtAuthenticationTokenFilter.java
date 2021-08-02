package com.yhcy.aqc.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * HTTP Request-Header 에서 JWT 값을 추출하고, JWT 값이 올바르다면 인증정보 JwtAuthenticationToken를 생성
 * 생성된 JwtAuthenticationToken 인스턴스는 SecurityContextHolder를 통해 Thread-Local 영역에 저장된다.
 * 인증이 완료된 JwtAuthenticationToken#principal 부분에는 JwtAuthentication 인스턴스가 set 된다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String headerKey;

    private final Jwt jwt;

    //요청이 들어오면 doFilter를 탄다.
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // SecurityContextHolder 에서 인증정보를 찾을 수 없다면...
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // HTTP 요청 Header 에서 JWT 값을 가져와본다. (jwtToken을 가지고 옵니다.)
            String authorizationToken = obtainAuthorizationToken(request);
            // JWT 값이 있다면, JWT 값을 검증하고 인증정보를 생성해 SecurityContextHolder 에 추가한다.
            if (authorizationToken != null) {
                try {
                    /**
                     * 이게 올바른 jwtToken이라고 한다면 jwtToken을 가져와서 디코딩합니다.
                     * payload 부분이 만들어짐 (verify 메서드 부분)
                     * claims를 보면 userKey, email, roles 등 여러 값이 담기게 됩니다.
                     */
                    Jwt.Claims claims = verify(authorizationToken);
                    log.debug("Jwt parse result: {}", claims);

                    // 만료 10분 전
                    if (canRefresh(claims, 10 * 60 * 1000)) {
                        String refreshedToken = jwt.refreshToken(authorizationToken);
                        response.setHeader(headerKey, refreshedToken);
                    }

                    Integer userKey = claims.userKey;

                    String userId = claims.userId;
                    String nickName = claims.nickName;

                    List<GrantedAuthority> authorities = obtainAuthorities(claims);

                    if (userKey != null &&  userId != null && nickName != null && authorities.size() > 0) {

                        //아까 Provider에서 만들었던 JwtToken과 동일
                        //여기서 만드는 인증 주체 타입은 '로그인 된 인증 주체' 이므로 Principal 타입에 JwtAuthentication(String이 X)이 담기게 됩니다.
                        JwtAuthenticationToken authentication =
                                new JwtAuthenticationToken(new JwtAuthentication(userKey, userId, nickName), null, authorities);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        //SecurityContextHolder에 set을 해주게 됩니다.
                        //얘는 ThreadLocal이라는 영역에 저장합니다. 이렇게 set을 하게 되면 Controller를 보면
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    log.warn("Jwt processing failed: {}", e.getMessage());
                }
            }
        } else {
            log.debug("SecurityContextHolder not populated with security token, as it already contained: '{}'",
            SecurityContextHolder.getContext().getAuthentication());
        }


        chain.doFilter(request, response);
    }

    private boolean canRefresh(Jwt.Claims claims, long refreshRangeMillis) {
        long exp = claims.exp();
        if (exp > 0) {
            long remain = exp - System.currentTimeMillis();
            return remain < refreshRangeMillis;
        }
        return false;
    }

    private List<GrantedAuthority> obtainAuthorities(Jwt.Claims claims) {
        String[] roles = claims.roles;
        return roles == null || roles.length == 0 ?
                Collections.emptyList() :
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(toList());
    }

    private String obtainAuthorizationToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(headerKey);
        if (jwtToken != null) {
            if (log.isDebugEnabled())
                log.debug("Jwt authorization api detected: {}", jwtToken);
            try {
                jwtToken = URLDecoder.decode(jwtToken, "UTF-8");
                String[] parts = jwtToken.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];
                    return BEARER.matcher(scheme).matches() ? credentials : null;
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
        }

        return null;
    }

    private Jwt.Claims verify(String token) {
        return jwt.verify(token);
    }
}
