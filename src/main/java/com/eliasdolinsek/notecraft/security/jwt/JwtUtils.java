package com.eliasdolinsek.notecraft.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("com.eliasdolinsek.notecraft.jwt-secret")
    private String jwtSecret;

    @Value("com.eliasdolinsek.notecraft.jwt-expiration-ms")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Jwts.SIG.HS256.key().build();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.error("Invalid JWT-Token: {}", token);
        } catch (IllegalArgumentException e) {
            logger.error("Empty JWT-Token: {}", token);
        }

        return false;
    }
}
