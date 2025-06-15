package com.example.booking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Book {

    private Long id;
    private String name;
    private String details;
    private BigDecimal price;
    private String image;
    private Boolean deleted = Boolean.FALSE;
    private String createdAt;
}
