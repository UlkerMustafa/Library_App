package org.libraryapp.service.impl;


import lombok.RequiredArgsConstructor;

import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dao.repository.UserRepository;
import org.libraryapp.dto.AuthRequestDto;
import org.libraryapp.exception.EmailAlreadyTakenException;
import org.libraryapp.exception.InvalidEmailException;
import org.libraryapp.util.helper.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSender emailSender;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repository.findByEmail(email).orElseThrow(() -> new InvalidEmailException(email));


        return new User(email, user.getPassword(), List.of());
    }

    public ResponseEntity<String> register(AuthRequestDto dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException("This email is already in use.");
        }
        var hashPassword = passwordEncoder.encode(dto.getPassword());

        var entity = UserEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(hashPassword)
                .userType(dto.getUserType())
                .build();


        repository.save(entity);
        var text = String.format("%s Library_App proqramina xos gelmisiz.", entity.getName());
        emailSender.sendEmail(entity.getEmail(), "Library_App", text);


        return ResponseEntity.status(HttpStatus.CREATED).body("Registration was successful.");
    }


}