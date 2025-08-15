
package com.example.wordindexer.service;

import com.example.wordindexer.entity.Word;
import com.example.wordindexer.model.WordCountDTO;
import com.example.wordindexer.repository.WordRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class WordService {
    private static final Logger log = LoggerFactory.getLogger(WordService.class);

    @NonNull
    private WordRepository repository;

    /**
     * Reads a file and persists all non-blank words to the database.
     */
    public void processFile(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        Arrays.stream(content.split("\\W+"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .forEach(token -> repository.save(new Word(null, token)));
        log.info("File processed and saved to DB: {}", filePath);
    }

    /**
     * Counts words starting with "M" or "m".
     */
    public WordCountDTO countWordsStartingWithM() {
        List<Word> words = repository.findWordsStartingWithM();
        long count = words.size();
        return new WordCountDTO(words, count);
    }

    /**
     * Returns all words longer than 5 characters.
     */
    public List<Word> getWordsLongerThanFive() {
        return repository.findWordsLongerThanFive();
    }

    /**
     * Searches for exact words in DB (case-insensitive) using indexed column.
     */
    public List<Word> searchExact(String keyword) {
        if (keyword == null || keyword.isBlank()) return List.of();

        String lower = keyword.toLowerCase();

        return repository.searchAllCases(lower);
    }
}
