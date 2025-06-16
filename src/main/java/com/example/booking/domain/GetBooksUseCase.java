package com.example.booking.domain;

import com.example.booking.persistence.BookEntity;
import com.example.booking.persistence.BookReadStorage;
import com.example.booking.persistence.BookRepository;
import com.example.booking.rest.BookResponseMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBooksUseCase {
    
    private final BookReadStorage bookReadStorage;

    public List<Book> getAllBooks() {
        return bookReadStorage.getAllBooks();
    }

    public List<Book> getSimilarBooks(Long bookId) {
        return bookReadStorage.calculateSimilarities(bookId);
    }
}
