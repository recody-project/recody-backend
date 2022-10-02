package com.recody.recodybackend.record.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ErrorType;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.common.utils.MessageUtils;
import com.recody.recodybackend.common.web.ErrorResponseBody;
import com.recody.recodybackend.record.RecodyRecordApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
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
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = RecodyRecordApplication.class)
@RequiredArgsConstructor
@Slf4j
@Order(999)
class RecordGlobalExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> on(Exception exception, HttpServletRequest request) {
        log.error("Global exception: {}", exception.toString());
        exception.printStackTrace();
        return ResponseEntity.internalServerError().body(ErrorResponseBody.internalServerErrorOf(exception, request.getRequestURI()));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorResponseBody> on(InternalServerError exception, HttpServletRequest request) {
        log.error("Global exception: {}", exception.toString());
        return ResponseEntity.internalServerError().body(ErrorResponseBody.internalServerErrorOf(exception, request.getRequestURI()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseBody> on(ApplicationException exception, HttpServletRequest request) {
        HttpStatus statusCode = exception.getStatusCode();
        ErrorType errorType = exception.getErrorCode();
        String message = ms.getMessage(errorType.getErrorCode(), null, request.getLocale());
        log.debug("Application Exception errorCode: {}, statusCode: {}", errorType, statusCode);
        return ResponseEntity.status(statusCode)
                             .body(ErrorResponseBody.type(errorType)
                                                    .message(message)
                                                    .requestUri(request.getRequestURI()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseBody> on(BindException exception, HttpServletRequest request) {
        log.debug("exception: {}", exception.getMessage());
        List<FieldError> allFieldErrors = exception.getFieldErrors();
        List<HashMap<String, String>> details = allFieldErrors.stream().map(this::createFieldErrorMap).collect(
                Collectors.toList());
        ErrorResponseBody body = ErrorResponseBody.type(exception)
                                                  .messageAnd(MessageUtils.seeDetails(exception.getMessage()))
                                                  .details(details)
                                                  .requestUri(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseBody> on(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.debug("exception: {}", exception.getMessage());
        String thatValue = null;
        if (exception.getMessage() != null) {
            String[] stringSplit = exception.getMessage().split(" String ", 2);
            log.info("split: {}", Arrays.toString(stringSplit));
            String[] valueSplit = stringSplit[1].split(":", 2);
            thatValue = valueSplit[0].replaceAll("\"", "");
            log.info("{} 는 적절한 타입이 아닙니다.", thatValue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponseBody.badRequestOf(exception,
                                                                  ms.getMessage("jsonParseError.arg", new String[]{thatValue}, request.getLocale()),
                                                                  request.getRequestURI()));
    }

    private HashMap<String, String> createFieldErrorMap(FieldError fieldError) {
        HashMap<String, String> map = new HashMap<>();
        map.put("field", fieldError.getField());
        map.put("message", fieldError.getDefaultMessage());
        return map;
    }

}
