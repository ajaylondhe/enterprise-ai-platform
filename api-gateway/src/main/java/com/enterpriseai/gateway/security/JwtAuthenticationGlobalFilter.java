package com.enterpriseai.gateway.security;

import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGlobalFilter
        implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    public JwtAuthenticationGlobalFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        /*
         * Authentication endpoints are public.
         */
        if (path.startsWith("/api/auth")) {

            return chain.filter(exchange);
        }

        /*
         * Actuator health is public.
         */
        if (path.equals("/actuator/health")) {

            return chain.filter(exchange);
        }

        String authorizationHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(
                                HttpHeaders.AUTHORIZATION
                        );

        /*
         * No Authorization header.
         */
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith(
                        "Bearer ")) {

            return unauthorized(exchange);
        }

        String token =
                authorizationHeader.substring(7);

        /*
         * Validate JWT.
         */
        if (!jwtService.isTokenValid(token)) {

            return unauthorized(exchange);
        }

        /*
         * JWT is valid.
         */
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(
            ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.UNAUTHORIZED
                );

        return exchange.getResponse()
                .setComplete();
    }

    @Override
    public int getOrder() {

        return -100;
    }
}