package com.example.wordindexer.model;

import com.example.wordindexer.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordCountDTO {
    private List<Word> wordList;
    private long count;
}