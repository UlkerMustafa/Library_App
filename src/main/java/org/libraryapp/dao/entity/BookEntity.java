package org.libraryapp.dao.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.libraryapp.util.enums.GenreEnum;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="library_book ")
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @PastOrPresent
    private Year publicationYear;
    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @ManyToMany(mappedBy = "books")
    private Set<UserEntity> users = new HashSet<>();


}
