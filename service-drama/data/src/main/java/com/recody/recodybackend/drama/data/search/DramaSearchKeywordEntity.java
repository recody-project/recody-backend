package com.recody.recodybackend.drama.data.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@Getter
public class DramaSearchKeywordEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true, nullable = false, updatable = false)
    private String text;
    
    @Column(nullable = false)
    private Integer count;
    
    public DramaSearchKeywordEntity(Long id, String text, Integer count) {
        this.id = id;
        this.text = text;
        this.count = 1;
    }
    
    public synchronized void increment(){
        this.count++;
    }
}
