package com.example.booking.domain;

import com.example.booking.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookViewReadStorage {

    private final BookViewRepository bookViewRepository;
    private final BookViewEntityMapper bookViewMapper;

    public List<BookView> getAllBookViews() {
        return bookViewRepository.findAll().stream()
                .map(bookViewMapper::toDomain)
                .toList();
    }
}
