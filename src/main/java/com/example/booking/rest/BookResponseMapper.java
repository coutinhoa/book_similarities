package com.example.booking.rest;

import com.example.booking.domain.Book;
import com.example.booking.domain.BookView;
import com.example.booking.persistence.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookResponseMapper {

    public BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .details(book.getDetails())
                .price(String.valueOf(book.getPrice()))
                .image(book.getImage())
                .deleted(book.getDeleted())
                .createdAt(book.getCreatedAt())
                .build();
    }

    public BookViewResponse toResponse(BookView bookView) {
        if (bookView == null) {
            return null;
        }
        return BookViewResponse.builder()
                .id(bookView.getId())
                .userEmail(bookView.getUserEmail())
                .build();
    }
}
