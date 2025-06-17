package com.example.booking.domain;

import com.example.booking.persistence.BookViewWriteStorage;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveBookViewsUseCase {

    private final BookViewWriteStorage bookWriteStorage;

    public List<BookView> saveBookViewsFromCsv(MultipartFile file) throws IOException {
        List<BookView> bookViews = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .build()) {

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    if (nextLine.length < 2) {
                        System.out.println("Skipping row with insufficient columns");
                        continue;
                    }
                    Long bookId = Long.valueOf(nextLine[0].trim());
                    String user = nextLine[1].trim();

                    BookView bookView = BookView.builder()
                            .userEmail(user)
                            .bookId(bookId)
                            .build();

                    bookViews.add(bookView);
            }
        } catch (CsvException e) {
                System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return bookWriteStorage.saveAll(bookViews);
    }

    public void createBookView(Long bookId, String userEmail) {
        BookView bookView = BookView.builder()
                .userEmail(userEmail)
                .bookId(bookId)
                .build();
        bookWriteStorage.saveBookView(bookView);
    }
}
