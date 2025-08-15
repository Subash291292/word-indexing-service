
package com.example.wordindexer.repository;

import com.example.wordindexer.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "SELECT * FROM words w WHERE LOWER(w.text) LIKE 'm%'", nativeQuery = true)
    List<Word> findWordsStartingWithM();

    @Query(value = "SELECT * FROM words w WHERE LENGTH(w.text) > 5", nativeQuery = true)
    List<Word> findWordsLongerThanFive();

    @Query(value = "SELECT * FROM words w " +
            "WHERE LOWER(w.text) = :text " +
            "   OR LOWER(w.text) LIKE CONCAT(:text, '%') " +
            "   OR LOWER(w.text) LIKE CONCAT('%', :text) " +
            "   OR LOWER(w.text) LIKE CONCAT('%', :text, '%')",
            nativeQuery = true)
    List<Word> searchAllCases(@Param("text") String text);
}
