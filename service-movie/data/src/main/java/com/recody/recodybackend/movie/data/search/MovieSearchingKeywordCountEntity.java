package com.recody.recodybackend.movie.data.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "movie_searching_keyword_count")
@Builder
@NoArgsConstructor
@Getter
public class MovieSearchingKeywordCountEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column( unique = true, nullable = false, updatable = false )
    private String keyword;
    
    @Column( nullable = false )
    private Integer count;
    
    public MovieSearchingKeywordCountEntity(String keyword) {
        this.id = null;
        this.keyword = keyword;
        this.count = 1;
    }
    
    public MovieSearchingKeywordCountEntity(Long id, String keyword, Integer count) {
        this.id = id;
        this.keyword = keyword;
        this.count = 1;
    }
    
    public synchronized void increment(){
        this.count++;
    }
}
