package com.example.booking.persistence;

import com.example.booking.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReadStorage {

    private final BookRepository bookRepository;
    private final BookViewRepository bookViewRepository;
    private final BookEntityMapper bookMapper;

    public List<Book> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream()
                .map(bookMapper::toDomain)
                .toList();
    }

    public List<Book> calculateSimilarities(Long receivedBookId) {
        //Build a list of all unique users
        List<BookViewEntity> bookViewEntities = bookViewRepository.findAll();
        Set<String> allUsers = new HashSet<>();
        for (BookViewEntity bookViewEntity : bookViewEntities) {
            allUsers.add(bookViewEntity.getUserEmail());
        }
        List<String> users = new ArrayList<>(allUsers);

        //Build the book vectors
        Map<Long, int[]> bookVectors = new HashMap<>();
        for (BookViewEntity bookViewEntity : bookViewEntities) {
            Long bookId = bookViewEntity.getBook().getId();
            String userEmail = bookViewEntity.getUserEmail();
            int userIndex = users.indexOf(userEmail);
            bookVectors.computeIfAbsent(bookId, k -> new int[users.size()])[userIndex] = 1;
            //A 1 means this user viewed the book, 0 means they didn’t.
            //bookVectors.get(1) = [0, 0, 1] means the first and second user did not see the book, and the third did.
        }

        //Calculate Cosine Similarity
        int[] targetVector = bookVectors.get(receivedBookId);
        if (targetVector == null) return Collections.emptyList();

        Map<Long, Double> similarityScores = new HashMap<>();

        for (Map.Entry<Long, int[]> entry : bookVectors.entrySet()) {
            Long otherBookId = entry.getKey();
            if (otherBookId.equals(receivedBookId)) continue;

            int[] otherVector = entry.getValue();
            double dot = 0, normA = 0, normB = 0;

            for (int i = 0; i < targetVector.length; i++) {
                dot += targetVector[i] * otherVector[i];
                normA += targetVector[i] * targetVector[i];
                normB += otherVector[i] * otherVector[i];
            }

            double similarity = (normA == 0 || normB == 0) ? 0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
            similarityScores.put(otherBookId, similarity);
        }


        //Sort and return top results
        return similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(10)
                .map(entry -> bookRepository.findById(entry.getKey()).orElse(null))
                .filter(Objects::nonNull)
                .map(bookMapper::toDomain)
                .collect(Collectors.toList());
    }
}
