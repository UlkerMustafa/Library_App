package org.libraryapp.mapper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dto.BookDto;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {
    public BookDto toDto(BookEntity entity) {
        return BookDto.builder()
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .publicationYear(entity.getPublicationYear())
                .genre(entity.getGenre())
                .stock(entity.getStock())
                .build();
    }

    public BookEntity toEntity(BookDto dto) {
        return BookEntity.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publicationYear(dto.getPublicationYear())
                .genre(dto.getGenre())
                .stock(dto.getStock())
                .build();
    }


}
