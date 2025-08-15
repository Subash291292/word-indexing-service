package com.example.wordindexer.service.helper;

import com.example.wordindexer.service.WordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @NonNull
    private final WordService wordService;

    @Value("${words.file.path}")
    private String wordsFilePath;

    @Override
    public void run(String... args) throws IOException {
        System.out.println("Loading words from file: " + wordsFilePath);
        wordService.processFile(wordsFilePath);
        System.out.println("Words loaded into the database successfully!");
    }
}
