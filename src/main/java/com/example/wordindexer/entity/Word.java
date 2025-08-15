
package com.example.wordindexer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "words", indexes = { @Index(name = "idx_word_text", columnList = "text") })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Word {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;
}
