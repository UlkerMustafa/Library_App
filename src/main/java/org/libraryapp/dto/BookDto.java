package org.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.libraryapp.util.enums.GenreEnum;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String title;
    private String author;
    private Year publicationYear;
    private GenreEnum genre;
    private Integer stock;
}
