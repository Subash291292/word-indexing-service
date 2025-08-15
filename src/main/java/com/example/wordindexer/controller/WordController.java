package com.example.wordindexer.controller;

import com.example.wordindexer.entity.Word;
import com.example.wordindexer.model.WordCountDTO;
import com.example.wordindexer.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/words", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WordController {

    @NonNull
    private final WordService service;

    @Operation(summary = "Process a file and save words to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File processed and words saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file path or file cannot be read")
    })
    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestParam String path) throws IOException {
        service.processFile(path);
        return ResponseEntity.ok("{\"message\":\"File processed & indexed\"}");
    }

    @Operation(summary = "Count words starting with 'M' or 'm'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("/count-m")
    public WordCountDTO countM() {
        return service.countWordsStartingWithM();
    }

    @Operation(summary = "Get all words longer than 5 characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Words retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("/longer-than-five")
    public List<Word> longerThanFive() {
        return service.getWordsLongerThanFive();
    }

    @Operation(summary = "Search for an exact word (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Words retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid keyword")
    })
    @GetMapping("/search")
    public List<Word> search(@RequestParam String keyword) {
        return service.searchExact(keyword);
    }
}