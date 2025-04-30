package org.libraryapp.service;

import org.libraryapp.dao.entity.UserEntity;
import org.libraryapp.util.enums.UserTypeEnum;

public interface UserService {
    void   registerUser(String name,String surname,String email, String password, UserTypeEnum userType);
}
