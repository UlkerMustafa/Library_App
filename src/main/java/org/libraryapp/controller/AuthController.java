package org.libraryapp.controller;


import lombok.RequiredArgsConstructor;
import org.libraryapp.service.UserService;
import org.libraryapp.util.enums.UserTypeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam UserTypeEnum userType){
        try{
            userService.registerUser(name,surname,email,password,userType);
            return "User registered successfully!";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }
//    @RequestParam — HTTP sorgularında URL query parametrlərini və ya form datanı Java metoduna almaq üçün istifadə olunur.
}
