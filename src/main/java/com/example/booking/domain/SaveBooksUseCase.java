package com.example.booking.domain;

import com.example.booking.persistence.BookWriteStorage;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveBooksUseCase {

    private final BookWriteStorage bookWriteStorage;

    public List<Book> saveBooks(MultipartFile file) throws IOException {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));) {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .withSkipLines(1)
                    .build()) {

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    if (nextLine.length < 5) {
                        System.out.println("Skipping row with insufficient columns: " + Arrays.toString(nextLine));
                        continue;
                    }
                    Long id = Long.valueOf(nextLine[0].trim());
                    String name = nextLine[1].trim();
                    String details = nextLine[2].trim();
                    BigDecimal price = new BigDecimal(nextLine[3].replace(",", ".").replace("$", "").replace("€", "").trim());
                    String image = nextLine[4].trim();

                    Book book = Book.builder()
                            .id(id)
                            .name(name)
                            .details(details)
                            .price(price)
                            .image(image)
                            .deleted(Boolean.FALSE)
                            .createdAt(LocalDateTime.now())
                            .build();

                    books.add(book);
                }
            }
        } catch (CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return bookWriteStorage.saveAll(books);
    }
}
