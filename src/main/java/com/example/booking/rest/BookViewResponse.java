package com.example.booking.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookViewResponse {
    private Long id;
    private String userEmail;
}
