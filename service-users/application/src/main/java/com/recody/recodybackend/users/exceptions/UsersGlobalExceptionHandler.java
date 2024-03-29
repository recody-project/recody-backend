package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ErrorType;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.common.utils.MessageUtils;
import com.recody.recodybackend.common.web.ErrorResponseBody;
import com.recody.recodybackend.users.RecodyUserApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = RecodyUserApplication.class)
@RequiredArgsConstructor
@Slf4j
@Order(998)
class UsersGlobalExceptionHandler {
    
    private final MessageSource ms;
    
    @ExceptionHandler( Exception.class )
    public ResponseEntity<ErrorResponseBody> on(Exception exception, HttpServletRequest request) {
        log.error("Global exception: {}", exception.toString());
        return ResponseEntity.internalServerError()
                             .body(ErrorResponseBody.internalServerErrorOf(exception, request.getRequestURI()));
    }
    
    @ExceptionHandler( InternalServerError.class )
    public ResponseEntity<ErrorResponseBody> on(InternalServerError exception, HttpServletRequest request) {
        log.error("Global exception: {}", exception.toString());
        return ResponseEntity.internalServerError()
                             .body(ErrorResponseBody.internalServerErrorOf(exception, request.getRequestURI()));
    }
    
    @ExceptionHandler( ApplicationException.class )
    public ResponseEntity<ErrorResponseBody> on(ApplicationException exception, HttpServletRequest request) {
        HttpStatus statusCode = exception.getStatusCode();
        ErrorType errorType = exception.getErrorCode();
        String message = resolveMessage(exception, request.getLocale(), errorType);
        log.debug("Application Exception errorCode: {}, statusCode: {}", errorType, statusCode);
        return ResponseEntity.status(statusCode)
                             .body(ErrorResponseBody.type(errorType)
                                                    .message(message)
                                                    .method(request.getMethod())
                                                    .requestUri(request.getRequestURI()));
    }
    
    @ExceptionHandler( BindException.class )
    public ResponseEntity<ErrorResponseBody> on(BindException exception, HttpServletRequest request) {
        log.debug("exception: {}", exception.getMessage());
        List<FieldError> allFieldErrors = exception.getFieldErrors();
        List<HashMap<String, String>> details = allFieldErrors.stream()
                                                              .map(this::createFieldErrorMap)
                                                              .collect(Collectors.toList());
        ErrorResponseBody body = ErrorResponseBody.type(exception)
                                                  .messageAnd(MessageUtils.seeDetails(exception.getMessage()))
                                                  .details(details)
                                                  .method(request.getMethod())
                                                  .requestUri(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    
    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity<ErrorResponseBody> on(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.debug("exception: {}", exception.getMessage());
        String message;
        message = resolveMessageWithException(exception, request);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponseBody.type(exception)
                                                    .message(message)
                                                    .method(request.getMethod())
                                                    .requestUri(request.getRequestURI()));
    }
    
    private HashMap<String, String> createFieldErrorMap(FieldError fieldError) {
        HashMap<String, String> map = new HashMap<>();
        map.put("field", fieldError.getField());
        map.put("message", fieldError.getDefaultMessage());
        return map;
    }
    
    private String resolveMessage(ApplicationException exception, Locale locale, ErrorType errorType) {
        String message;
        try {
            message = ms.getMessage(errorType.getErrorCode(), null, locale);
            return message;
        } catch ( NoSuchMessageException ignored ) {
        }
        if ( exception.getMessage() != null ) {
            message = exception.getMessage();
        }
        else {
            message = "No available message";
        }
        return message;
    }
    
    private String resolveMessageWithException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        String message;
        String thatValue;
        if ( exception.getMessage() != null ) {
            try {
                String[] stringSplit = exception.getMessage().split(" String ", 2);
                log.info("split: {}", Arrays.toString(stringSplit));
                String[] valueSplit = stringSplit[1].split(":", 2);
                thatValue = valueSplit[0].replaceAll("\"", "");
                log.info("{} 는 적절한 타입이 아닙니다.", thatValue);
                message = ms.getMessage("jsonParseError.arg", new String[]{thatValue}, request.getLocale());
            } catch ( Exception ignored ) {
                message = ms.getMessage("jsonParseError", null, request.getLocale());
            }
        }
        else {
            message = "No available message";
        }
        return message;
    }
}
