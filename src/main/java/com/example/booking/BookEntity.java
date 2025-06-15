package com.example.booking;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Table(name = "T_BOOK")
@Entity
@SQLDelete(sql = "UPDATE T_BOOK SET deleted = true WHERE id =?")
@Where(clause = "deleted=false")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "created_at", nullable = false)
    private String createdAt;
}
