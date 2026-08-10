package com.enterpriseai.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtService {

    private final SecretKey secretKey;

    public JwtService(String secret) {

        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must contain at least 32 characters"
            );
        }

        this.secretKey =
                Keys.hmacShaKeyFor(
                        secret.getBytes(StandardCharsets.UTF_8)
                );
    }


    public String extractUsername(
            String token) {

        return extractClaims(token)
                .getSubject();
    }


    public String extractRole(
            String token) {

        return extractClaims(token)
                .get(
                        SecurityConstants.ROLE_CLAIM,
                        String.class
                );
    }


    public boolean isTokenValid(
            String token) {

        try {

            Claims claims =
                    extractClaims(token);

            Date expiration =
                    claims.getExpiration();

            return expiration != null &&
                    expiration.after(new Date());

        } catch (Exception e) {

            return false;
        }
    }


    private Claims extractClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}