package com.example.booking.persistence;

import com.example.booking.domain.Book;
import com.example.booking.domain.BookView;
import org.springframework.stereotype.Component;

@Component
public class BookViewEntityMapper {

    public BookViewEntity toEntity(BookView bookView) {
        if (bookView == null) {
            return null;
        }
        return BookViewEntity.builder()
                .id(bookView.getId())
                .userEmail(bookView.getUserEmail())
                .build();
    }

    public BookView toDomain(BookViewEntity bookViewEntity) {
        if (bookViewEntity == null) {
            return null;
        }
        return BookView.builder()
                .id(bookViewEntity.getId())
                .userEmail(bookViewEntity.getUserEmail())
                .build();
    }
}
