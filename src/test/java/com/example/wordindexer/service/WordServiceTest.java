
package com.example.wordindexer.service;

import com.example.wordindexer.entity.Word;
import com.example.wordindexer.model.WordCountDTO;
import com.example.wordindexer.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
class WordServiceTest {

    @InjectMocks
    private WordService service;

    @Mock
    private WordRepository repository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testProcessFile_SavesAllWords() throws IOException {
        // Arrange
        String content = "Magic Moon Code";
        Path tempFile = Files.createTempFile("test-words", ".txt");
        Files.writeString(tempFile, content);

        // Act
        service.processFile(tempFile.toString());

        // Assert
        verify(repository, times(3)).save(any(Word.class));
    }

    @Test
    public void testCountWordsStartingWithM_ReturnsCorrectDTO() {
        // Arrange
        Word w1 = new Word(1L, "Magic");
        Word w2 = new Word(2L, "Moon");
        when(repository.findWordsStartingWithM()).thenReturn(List.of(w1, w2));

        // Act
        WordCountDTO dto = service.countWordsStartingWithM();

        // Assert
        assertNotNull(dto);
        assertEquals(2, dto.getCount());
        assertEquals(2, dto.getWordList().size());
        assertEquals("Magic", dto.getWordList().get(0).getText());
        verify(repository, times(1)).findWordsStartingWithM();
    }

    @Test
    public void testGetWordsLongerThanFive_ReturnsWordList() {
        // Arrange
        Word w1 = new Word(1L, "Morning");
        Word w2 = new Word(2L, "Magic");
        when(repository.findWordsLongerThanFive()).thenReturn(List.of(w1, w2));

        // Act
        List<Word> result = service.getWordsLongerThanFive();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findWordsLongerThanFive();
    }

    @Test
    void testSearchExact_ReturnsMatchingWords() {
        // Arrange
        String keyword = "Magic";
        Word w1 = new Word(1L, "Magic");
        when(repository.searchAllCases(keyword.toLowerCase())).thenReturn(List.of(w1));

        // Act
        List<Word> result = service.searchExact(keyword);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Magic", result.get(0).getText());
        verify(repository, times(1)).searchAllCases(keyword.toLowerCase());
    }

    @Test
    public void testSearchExact_ReturnsEmptyForNullOrBlank() {
        assertEquals(0, service.searchExact(null).size());
        assertEquals(0, service.searchExact("  ").size());
        verify(repository, never()).searchAllCases(anyString());
    }
}
