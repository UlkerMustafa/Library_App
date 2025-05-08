package org.libraryapp.dao.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.usertype.UserType;
import org.libraryapp.util.enums.UserTypeEnum;

import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<BookEntity> books = new HashSet<>();
}
