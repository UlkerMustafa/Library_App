package org.libraryapp.controller;


import lombok.RequiredArgsConstructor;
import org.libraryapp.dao.repository.UserRepository;
import org.libraryapp.dto.AuthRequestDto;
import org.libraryapp.dto.LoginRequestDto;
import org.libraryapp.exception.EmailAlreadyTakenException;
import org.libraryapp.service.impl.UserDetailsServiceImpl;
import org.libraryapp.util.filter.JwtFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl userService;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtFilter jwtFilter;


    @PostMapping("/register")
    public String register(@RequestBody AuthRequestDto dto) {
        try {
            userService.register(dto);
            return "User registered successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        var userOptional = repository.findByEmail(dto.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password is incorrect");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(), dto.getPassword()));
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password is incorrect");
        }
        var user = userOptional.get();
        var token = jwtFilter.tokenGenerate(user.getID());



        return ResponseEntity.ok().body(token);
    }


}
