package com.example.booking.persistence;

import com.example.booking.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookWriteStorage {

    private final BookRepository bookRepository;
    private final BookEntityMapper bookEntityMapper;

    public List<Book> saveAll(List<Book> books) {
        List<BookEntity> bookEntities = books.stream()
                .map(bookEntityMapper::toEntity)
                .toList();
        List<BookEntity> booksSaved = bookRepository.saveAll(bookEntities);
        return booksSaved.stream()
                .map(bookEntityMapper::toDomain)
                .toList();
    }
}
