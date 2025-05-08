package org.libraryapp.service;

import org.libraryapp.dto.BookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<String> createBook(BookDto dto);
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    void deleteBook(Long id);
}
