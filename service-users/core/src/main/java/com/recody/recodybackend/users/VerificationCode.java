package com.recody.recodybackend.users;

import lombok.NonNull;

import java.util.Random;

/**
 * 비밀번호 재설정을 위한 인증 코드 입니다.
 * <li> 값은 String 타입입니다. </li>
 * <li> 6자리의 자연수로 이루어진 문자열입니다. </li>
 * @author motive
 */
public class VerificationCode {
    
    private final String value;
    
    private VerificationCode(String value) {
        this.value = value;
    }
    
    public static VerificationCode generate(){
        String value = generateValue();
        return new VerificationCode( value );
        
    }
    
    @NonNull
    private static String generateValue() {
        Random r = new Random();
        int checkNum = r.nextInt( 888888 ) + 111111;
        return String.valueOf( checkNum );
    }
    
    public String getValue() {
        return value;
    }
}
