package com.example.booking.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Table(name = "T_BOOK")
@Entity
@SQLDelete(sql = "UPDATE T_BOOK SET deleted = true WHERE id =?")
@Where(clause = "deleted=false")
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BookViewEntity> views;
}
