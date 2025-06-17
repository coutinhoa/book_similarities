package com.example.booking.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookView {
    private Long id;
    private String userEmail;
    private Long bookId;
}
