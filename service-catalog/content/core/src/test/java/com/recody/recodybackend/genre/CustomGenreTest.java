package com.recody.recodybackend.genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CustomGenreTest {
    
    @ParameterizedTest
    @CsvSource(value = {"g-1", "g-2", "g-9999"})
    @DisplayName( "커스텀 장르의 Id는 로 시작한다. " )
    void customGenreTest(String genreId) {
        // given
        
        // when
    
        // then
        assertThatNoException().isThrownBy( () ->  CustomGenreId.of( genreId ));
    }
    
    @ParameterizedTest
    @CsvSource(value = {"g3-1", "gg-2", "g9999", "mg-1"})
    @DisplayName( "잘못된 커스텀 ID 형태" )
    void customGenreTestFail(String genreId) {
        // given
        
        // when
        
        // then
        assertThatThrownBy( () ->  CustomGenreId.of( genreId ));
    }
    
    @ParameterizedTest
    @CsvSource(value = {"aaaaabbbbb", "123", "가나다라마바사아자차", "ㄷㄹㄷㄹ"})
    @DisplayName( "정상적인 장르 이름 형태" )
    void customGenreNameTest(String genreName) {
        // given
        
        // when
        
        // then
        assertThatNoException().isThrownBy( () ->  CustomGenreName.of( genreName ));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"aaaaabbbbbbbb", " ", "가나다라마바사아자차g", ""})
    @DisplayName( "잘못된 장르 이름 형태" )
    void customGenreNameTestFail(String genreName) {
        // given
        
        // when
        
        // then
        assertThatThrownBy( () ->  CustomGenreName.of( genreName ));
    }
}