package com.recody.recodybackend.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
public class LanguageUtils {
    
    
    /**
     * 텍스트가 어느나라 언어인지 구분합니다.
     * @param text 텍스트
     * @return 텍스트의 언어에 따른 Locale 객체
     */
    public static Locale languageOf(String text) {
        if ( isEnglishOnly( text ) ) {
            log.trace( "English text: {}", text );
            return Locale.ENGLISH;
        }
        else if ( isKoreanOnly(text) ) {
            log.trace( "Korean text: {}", text );
            return Locale.KOREAN;
        }
        else {
            log.trace( "else text: {}", text );
            return Locale.ENGLISH;
        }
    }
    
    public static boolean isKoreanOnly(String text) {
        return Pattern.matches( "^[가-힣0-9\\s]+", text );
    }
    
    public static boolean isEnglishOnly(String text) {
        return Pattern.matches( "^[a-zA-Z0-9\\s]+", text );
    }
}
