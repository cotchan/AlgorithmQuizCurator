package com.yhcy.aqc.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**").anyRequest();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //SpringSecurity import 시 기본으로 모든 요청에 대해 로그인을 요구하는 기능 일시 중단
        //이 후 해당 기능을 이용하려면 이 코드는 삭제해야함
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll().anyRequest().
                authenticated().and().formLogin().disable();
    }
}
