package org.libraryapp.dao.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank
    private String name;
    @NotBlank
    private  String surname;
    @NotBlank
    @Email
    @Column(unique = true,nullable = false)
    private String email;
    @NotBlank
    private String  password;

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}
