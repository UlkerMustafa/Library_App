package org.libraryapp.dao.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.usertype.UserType;
import org.libraryapp.util.enums.UserTypeEnum;

@Entity
@Table(name="library_user ")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotEmpty
    private String name;
    @NotEmpty
    private  String surname;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String  password;

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}
