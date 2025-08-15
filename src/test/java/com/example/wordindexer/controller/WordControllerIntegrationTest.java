package com.example.wordindexer.controller;

import com.example.wordindexer.entity.Word;
import com.example.wordindexer.model.WordCountDTO;
import com.example.wordindexer.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @Test
    void testProcessFile() throws Exception {
        String path = "dummy/path/words.txt";
        doNothing().when(wordService).processFile(path);

        mockMvc.perform(post("/words/process")
                        .param("path", path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"File processed & indexed\"}"));

        verify(wordService, times(1)).processFile(path);
    }

    @Test
    void testLongerThanFive() throws Exception {
        Word w1 = new Word(1L, "Magic");
        Word w2 = new Word(2L, "Morning");
        when(wordService.getWordsLongerThanFive()).thenReturn(List.of(w1, w2));

        mockMvc.perform(get("/words/longer-than-five")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Magic"));

        verify(wordService, times(1)).getWordsLongerThanFive();
    }


    @Test
    void testCountM() throws Exception {
        Word w1 = new Word(1L, "Magic");
        Word w2 = new Word(2L, "Moon");
        WordCountDTO dto = new WordCountDTO(List.of(w1, w2), 2);

        when(wordService.countWordsStartingWithM()).thenReturn(dto);

        mockMvc.perform(get("/words/count-m")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.wordList[0].text").value("Magic"));

        verify(wordService, times(1)).countWordsStartingWithM();
    }

    @Test
    void testSearchExact() throws Exception {
        Word w1 = new Word(1L, "Magic");
        String keyword = "Magic";
        when(wordService.searchExact(keyword)).thenReturn(List.of(w1));

        mockMvc.perform(get("/words/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Magic"));

        verify(wordService, times(1)).searchExact(keyword);
    }
}

