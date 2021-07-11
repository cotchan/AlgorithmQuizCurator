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

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-resources", "/webjars/**", "/static/**", "/templates/**", "/h2/**");
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, JwtAuthenticationProvider authenticationProvider) {
        // AuthenticationManager 는 AuthenticationProvider 목록을 지니고 있다.
        // 이 목록에 JwtAuthenticationProvider 를 추가한다.
        builder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, UserService userService) {
        return new JwtAuthenticationProvider(jwt, userService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 요청이 거절된 경우는 DecisionManager가 튕겨낸 것입니다.
    // 모든 voter가 승인을 해야하는 것으로 셋팅을 했는데 WebExpressionVoter가 승인을 하지 않은 것입니다.
    // WebExpressionVoter는 WebSecurityConfigure::configure에 있는 설정들을 판단하는 애
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();

        decisionVoters.add(new WebExpressionVoter());
        // voter 목록에 connectionBasedVoter 를 추가함
        // 모든 voter 승인해야 해야함
        return new UnanimousBased(decisionVoters);
    }

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
                .antMatchers("/user/login").permitAll()
                .antMatchers("/api/_hcheck").permitAll()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/user/exists").permitAll()
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
