package com.example.booking;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {
    private Long id;
    private String name;
    private String details;
    private String price;
    private String image;
    private Boolean deleted = Boolean.FALSE;
    private String createdAt;
}
