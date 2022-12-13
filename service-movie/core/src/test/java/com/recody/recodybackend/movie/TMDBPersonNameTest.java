package com.recody.recodybackend.movie;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TMDBPersonNameTest {
    
    @Test
    void of() {
    }
    
    @Test
    void firstKoreanNameOf() {
    
        List<String> names = List.of( "aaa", "bbb", "ccc", "한글 이름", "ddd");
        TMDBPersonName tmdbPersonName = TMDBPersonName.firstKoreanNameOf( names );
        assertThat( tmdbPersonName ).isNotNull();
        System.out.println( tmdbPersonName );
    }
}