package com.gr8fulnez.Jaiz_bank_app.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
//        if (username == null) {
//            throw new IllegalArgumentException("Authentication name cannot be null");
//        }
//
//        if (jwtExpirationDate <= 0) {
//            throw new IllegalArgumentException("JWT expiration date must be positive");
//        }

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key(){
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException |
                 MalformedJwtException |
                 SecurityException |
                 IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
