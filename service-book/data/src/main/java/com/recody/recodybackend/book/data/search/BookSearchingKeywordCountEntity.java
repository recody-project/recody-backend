package com.recody.recodybackend.book.data.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_searching_keyword_count")
@Builder
@NoArgsConstructor
@Getter
public class BookSearchingKeywordCountEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String text;

    @Column(nullable = false)
    private Integer count;

    public BookSearchingKeywordCountEntity(Long id, String text, Integer count) {
        this.id = id;
        this.text = text;
        this.count = 1;
    }

    public synchronized void increment(){
        this.count++;
    }
}
