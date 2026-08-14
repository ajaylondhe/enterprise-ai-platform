package com.enterpriseai.ai.security;

import com.enterpriseai.common.security.JwtAuthenticationFilter;
import com.enterpriseai.common.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtService jwtService(
            @Value("${jwt.secret}") String secret) {

        return new JwtService(secret);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService) {

        return new JwtAuthenticationFilter(jwtService);
    }
}