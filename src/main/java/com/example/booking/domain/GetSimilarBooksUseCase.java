package com.example.booking.domain;

import com.example.booking.persistence.BookReadStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSimilarBooksUseCase {
    
    private final BookViewReadStorage bookViewReadStorage;
    private final BookReadStorage bookReadStorage;

    public List<Book> getSimilarBooks(Long receivedBookId) {
        //Build a list of all unique users
        List<BookView> bookViewEntities = bookViewReadStorage.getAllBookViews();
        Set<String> allUsers = new HashSet<>();
        for (BookView bookViewEntity : bookViewEntities) {
            allUsers.add(bookViewEntity.getUserEmail());
        }
        List<String> users = new ArrayList<>(allUsers);

        //Build the book vectors
        Map<Long, int[]> bookVectors = new HashMap<>();
        for (BookView bookView : bookViewEntities) {
            Long bookId = bookView.getId();
            String userEmail = bookView.getUserEmail();
            int userIndex = users.indexOf(userEmail);
            bookVectors.computeIfAbsent(bookId, k -> new int[users.size()])[userIndex] = 1;
            //A 1 means this user viewed the book, 0 means they didn’t.
            //bookVectors.get(1) = [0, 0, 1] means the first and second user did not see the book, and the third did.
        }

        //Calculate Cosine Similarity
        int[] targetBook = bookVectors.get(receivedBookId);
        if (targetBook == null) return Collections.emptyList();

        Map<Long, Double> similarityScores = new HashMap<>();

        for (Map.Entry<Long, int[]> entry : bookVectors.entrySet()) {
            Long currentBookId = entry.getKey();
            if (currentBookId.equals(receivedBookId)) continue;

            int[] currentBook = entry.getValue();
            double numerator = 0, normA = 0, normB = 0;

            for (int i = 0; i < targetBook.length; i++) {
                numerator += targetBook[i] * currentBook[i];
                normA += targetBook[i] * targetBook[i];
                normB += currentBook[i] * currentBook[i];
            }

            double similarity = (normA == 0 || normB == 0) ? 0 : numerator / (Math.sqrt(normA) * Math.sqrt(normB));
            similarityScores.put(currentBookId, similarity);
        }

        //Sort and return top results
        return similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(10)
                .map(entry -> bookReadStorage.findById(entry.getKey()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

/*
Value Range: The cosine similarity ranges from -1 to 1:
1: Indicates that the vectors are identical in orientation (perfectly similar).
0: Indicates that the vectors are orthogonal (no similarity).
-1: Indicates that the vectors are diametrically opposed (completely dissimilar).
* */
