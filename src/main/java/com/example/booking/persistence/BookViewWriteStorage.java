package com.example.booking.persistence;


import com.example.booking.domain.Book;
import com.example.booking.domain.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookViewWriteStorage {

    private final BookViewRepository bookViewRepository;
    private final BookViewEntityMapper bookViewEntityMapper;

    public List<BookView> saveAll(List<BookView> books) {
        List<BookViewEntity> bookEntities = books.stream()
                .map(bookViewEntityMapper::toEntity)
                .toList();
        List<BookViewEntity> bookViewsSaved = bookViewRepository.saveAll(bookEntities);
        return bookViewsSaved.stream()
                .map(bookViewEntityMapper::toDomain)
                .toList();
    }

    public void saveBookView(BookView bookView) {
        BookViewEntity bookViewEntity = bookViewEntityMapper.toEntity(bookView);
        bookViewRepository.save(bookViewEntity);
    }
}
