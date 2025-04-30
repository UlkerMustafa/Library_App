package org.libraryapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.libraryapp.util.enums.UserTypeEnum;
import org.springframework.web.bind.annotation.RequestParam;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequestDto {

    String name;
    String surname;
    String email;
    String password;
    UserTypeEnum userType;


}
