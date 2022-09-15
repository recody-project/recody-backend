package com.recody.recodybackend.common.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    
    /*
    * 커스텀으로 정의된 에러코드. */
    private final ErrorType customErrorType;
    
    /*
    * 에러 응답 handling 에 실수를 방지하고자 spring web 의 상태코드 enum 사용. */
    private final HttpStatus statusCode;
    
    /*
    * 메시지를 직접 입력할 때의 생성자. */
    public ApplicationException(ErrorType customErrorType, HttpStatus statusCode, String message) {
        super(message);
        this.customErrorType = customErrorType;
        this.statusCode = statusCode;
    }
    /*
    * 에러코드, 상태코드만으로 처리할 때의 생성자. */
    public ApplicationException(ErrorType customErrorType, HttpStatus statusCode) {
        this.customErrorType = customErrorType;
        this.statusCode = statusCode;
    }
    
    public ErrorType getErrorCode() { return customErrorType; }
    public HttpStatus getStatusCode() { return statusCode; }
}
