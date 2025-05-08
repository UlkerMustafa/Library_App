package org.libraryapp.service.impl;


import lombok.RequiredArgsConstructor;
import org.libraryapp.util.filter.JwtFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtFilter jwtHelper;

    public ResponseEntity<Long> validateToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (!jwtHelper.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = jwtHelper.extractUserId(token);
        return ResponseEntity.ok(userId);
    }

}