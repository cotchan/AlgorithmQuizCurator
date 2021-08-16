package com.yhcy.aqc.configure;

import com.yhcy.aqc.configure.support.SimplePageableArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Bean
    public SimplePageableArgumentResolver simplePageableArgumentResolver() {
        return new SimplePageableArgumentResolver();
    }

    //offset,limit를 Query Parameter로 받아 Pageable 구현체를 생성해주는 HandlerMethodArgumentResolver 인터페이스 구현체
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(simplePageableArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000");
    }
}
