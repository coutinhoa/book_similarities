package com.example.booking;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public BookEntity toEntity(Book book) {
        if (book == null) {
            return null;
        }
        return BookEntity.builder()
                .id(book.getId())
                .name(book.getName())
                .details(book.getDetails())
                .price(book.getPrice())
                .image(book.getImage())
                .deleted(book.getDeleted())
                .createdAt(book.getCreatedAt())
                .build();
    }

    public Book toDomain(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }
        return Book.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .details(bookEntity.getDetails())
                .price(bookEntity.getPrice())
                .image(bookEntity.getImage())
                .deleted(bookEntity.getDeleted())
                .createdAt(bookEntity.getCreatedAt())
                .build();
    }

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
                .createdAt(book.getCreatedAt())
                .build();

    }
}
