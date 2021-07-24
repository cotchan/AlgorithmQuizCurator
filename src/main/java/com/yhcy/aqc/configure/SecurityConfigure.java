package com.yhcy.aqc.configure;

import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.security.*;
import com.yhcy.aqc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    private final Jwt jwt;

    private final JwtTokenConfigure jwtTokenConfigure;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    /**
     * JwtTokenConfigure.java 클래스 설정값을 이용하여 TokenFilter를 빈으로 등록
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    /**
     * 아래 패턴을 가진 URL에는 접근할 수 없도록 설정
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-resources", "/webjars/**", "/static/**", "/templates/**", "/h2/**");
    }

    /**
     * AuthenticationManager가 가지고 있는 AuthenticationProvider 목록에
     * JwtAuthenticationProvider를 추가합니다.
     * 그러면 AuthenticationManager가 인증을 할 때 JwtAuthenticationProvider를 사용하여 인증을 합니다.
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, JwtAuthenticationProvider authenticationProvider) {
        // AuthenticationManager 는 AuthenticationProvider 목록을 지니고 있다.
        // 이 목록에 JwtAuthenticationProvider 를 추가한다.
        builder.authenticationProvider(authenticationProvider);
    }

    /**
     * AuthenticationProvider 인터페이스를 구현한 구현체 빈으로 등록
     */
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, UserService userService) {
        return new JwtAuthenticationProvider(jwt, userService);
    }

    /**
     * AuthenticationManager 빈 등록
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * PasswordEncoder 빈으로 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 요청이 거절된 경우는 DecisionManager가 튕겨낸 것입니다.
    // 모든 voter가 승인을 해야하는 것으로 셋팅을 했는데 WebExpressionVoter가 승인을 하지 않은 것입니다.
    // WebExpressionVoter는 WebSecurityConfigure::configure에 있는 설정들을 판단하는 애

    /**
     * 인증 과정이 끝난 후 인가 과정에서 사용하는 AccessDecisionManager 빈으로 등록
     * 인가: 인증에 성공한 사용자가 해당 자원(URL)에 대해 접근 권한 유무를 판단 (보통 ROLE_로 판단한다고 보면 됨)
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();

        decisionVoters.add(new WebExpressionVoter());
        // AccessDecisionManager가 가지고 있는 모든 voter가 접근을 승인해야 해당 자원에 대한 인가를 해주는 정책 사용
        return new UnanimousBased(decisionVoters);
    }

    /**
     * SpringSecurity 적용에 따라 URL 요청이 들어왔을 때 처리 방법 및 보호되는 리소스를 정의
     * 필터 체인에 jwtAuthenticationTokenFilter 등록
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                // JWT 인증을 사용하므로 무상태(STATELESS) 전략 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 보호되는 리소스를 정의합니다.
                // permitAll은 '권한이 없는 누구라도 호출할 수 있다'라는 뜻.
                // 위 이외의 모든 api는 User라는 권한이 있어야 합니다.
                .antMatchers("/api/_hcheck").permitAll()
                //FIXME: /api/problems 삭제 필요
                .antMatchers("/api/problems").permitAll()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().permitAll()
                .and()
                // JWT 인증을 사용하므로 form 로긴은 비활성처리
                .formLogin()
                .disable();
        http
                // 필터 체인 변경
                // UsernamePasswordAuthenticationFilter 앞에 jwtAuthenticationTokenFilter 를 추가한다.
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
