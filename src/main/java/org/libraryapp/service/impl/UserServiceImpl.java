package org.libraryapp.service.impl;


import lombok.RequiredArgsConstructor;
import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.dao.repository.UserRepository;
import org.libraryapp.service.UserService;
import org.libraryapp.util.enums.UserTypeEnum;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;


    @Override
    public void registerUser(String name, String surname, String email, String password, UserTypeEnum userType) {
       //istifadeci email yoxlayiriq
     if (userRepository.findByEmail(email).isPresent()){
         throw  new RuntimeException("Email artiq mövcuddur.");
     }
     //yeni istifadeci yaradiriq

        UserEntity user = new UserEntity();

        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserType(userType);

        //istifadecini qeydiyyatdan keciririk
         userRepository.save(user);
    }
}
