package com.example.booking;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBooksUseCase {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<Book> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream()
                .map(bookMapper::toDomain)
                .toList();
    }
}
