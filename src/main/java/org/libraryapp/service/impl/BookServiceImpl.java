package org.libraryapp.service.impl;


import lombok.RequiredArgsConstructor;
import org.libraryapp.dao.entity.BookEntity;
import org.libraryapp.dao.repository.BookRepository;
import org.libraryapp.dto.BookDto;
import org.libraryapp.mapper.BookMapper;
import org.libraryapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Override
    public ResponseEntity<String> createBook(BookDto dto) {
        BookEntity book=bookMapper.toEntity(dto);
        BookEntity saved=bookRepository.save(book);
        bookMapper.toDto(saved);
        return ResponseEntity.ok("Created book");
    }

    @Override
    public List<BookDto> getAllBooks() {
        return  bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
