package com.example.booking.persistence;

import com.example.booking.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReadStorage {

    private final BookRepository bookRepository;
    private final BookEntityMapper bookMapper;

    public List<Book> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream()
                .map(bookMapper::toDomain)
                .toList();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDomain);
    }
}