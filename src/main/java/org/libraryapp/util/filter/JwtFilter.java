package org.libraryapp.util.filter;

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

    public String extractUsername(String token) {
        String subject = tokenFromDecoder(token);
        if (subject == null) {
            throw new RuntimeException("token is invalid");
        }
        return subject;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}