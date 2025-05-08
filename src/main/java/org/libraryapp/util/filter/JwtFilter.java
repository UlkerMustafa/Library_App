package org.libraryapp.util.filter;

import io.jsonwebtoken.Claims;
import  io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtFilter {
    @Value("${jwt.expire}")
    private Long EXPIRE;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String tokenGenerate(Long id){
        return Jwts.builder()
                .subject(id.toString())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(secretKey())
                .compact();
    }

    public String tokenFromDecoder(String token){
        try {
            return Jwts.parser()
                    .verifyWith(secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            System.out.println("[JwtFilter] Token decode edilemedi: " + e.getMessage());
            return null;
        }
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        String subject = claims.getSubject();
        if (subject == null) {
            throw new RuntimeException("Subject (User ID) claim is missing or null in the token");
        }
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Subject (User ID) claim is not a valid number: " + subject, e);
        }
    }


    public boolean isTokenValid(String token) {
        try {
            if(token==null){
                throw new RuntimeException("token bosdur");
            }
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

}}
