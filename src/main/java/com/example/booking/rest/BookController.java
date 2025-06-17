package com.example.booking.rest;

import com.example.booking.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final SaveBooksUseCase saveBooksUseCase;
    private final BookResponseMapper bookMapper;
    private final GetBooksUseCase getBooksUseCase;
    private final GetSimilarBooksUseCase getSimilarBooksUseCase;
    private final LoginValidationUseCase loginValidationUseCase;
    private final SaveBookViewsUseCase saveBookViewsUseCase;

    @PostMapping
    public ResponseEntity<List<BookResponse>> saveBooks(@RequestParam("file") MultipartFile file) throws IOException {
        List<Book> books = saveBooksUseCase.saveBooks(file);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                books.stream()
                        .map(bookMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        List<Book> books = getBooksUseCase.getAllBooks();

        return ResponseEntity.ok(
                books.stream()
                        .map(bookMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "password", required = true) String password) {

        boolean isValid = loginValidationUseCase.validateLogin(email);

        if (isValid) {
            URI redirectUri = URI.create("/api/v1/book");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(redirectUri)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/views")
    public ResponseEntity<List<BookViewResponse>> saveBookViews(@RequestParam("file") MultipartFile file) throws IOException {
        List<BookView> bookViews = saveBookViewsUseCase.saveBookViewsFromCsv(file);
        if (bookViews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                bookViews.stream()
                        .map(bookMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/similarity")
    public ResponseEntity<List<BookResponse>> getSimilarity(
            @RequestParam(name = "bookId") Long bookId,
            @RequestParam(name = "userEmail") String userEmail) {

        saveBookViewsUseCase.createBookView(bookId, userEmail);
        List<Book> similarBooks = getSimilarBooksUseCase.getSimilarBooks(bookId);

        if (similarBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                similarBooks.stream()
                        .map(bookMapper::toResponse)
                        .toList()
        );
    }
}
