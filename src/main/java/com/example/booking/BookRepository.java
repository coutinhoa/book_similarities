package com.example.booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // Custom query methods can be defined here if needed
    // For example:
    // List<BookEntity> findByNameContaining(String name);

    // The default methods from JpaRepository will provide basic CRUD operations

}
