package com.example.booking;

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

    private final ImportBooksFromCsvUseCase importBooksFromCsvUseCase;
    private final BookMapper bookMapper;
    private final GetBooksUseCase getBooksUseCase;
    private final LoginValidationUseCase loginValidationUseCase;

    @PostMapping
    public ResponseEntity<List<BookResponse>> saveBooks(@RequestParam("file") MultipartFile file) throws IOException {
        List<Book> books = importBooksFromCsvUseCase.processAndSaveCsv(file);
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
            URI redirectUri = URI.create("/");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(redirectUri)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
