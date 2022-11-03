package com.recody.recodybackend.common.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recody.recodybackend.common.exceptions.ErrorType;

import java.io.IOException;
import java.util.Locale;

/**
 * @author motive
 */
public class ErrorResponseBody {
    private static final String INTERNAL_SERVER_ERROR = "요청을 처리하지 못했습니다.";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private ErrorResponse error;
    
    private ErrorResponseBody(ErrorResponse error) { this.error = error; }
    
    
    public static ErrorResponseBody internalServerErrorOf(Exception exception, String requestUri) {
        String message = exception.getMessage();
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message == null ? INTERNAL_SERVER_ERROR : message, requestUri));
    }
    
    /* 사전에 정의되지 않은 예외를 상태코드로 응답하기 */
    public static ErrorResponseBody badRequestOf(Exception exception, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message, requestUri));
    }
    
    public static ErrorResponseBody forbiddenOf(Exception exception, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message, requestUri));
    }
    
    public static ErrorResponseBody notFoundOf(Exception exception, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message, requestUri));
    }
    
    public static ErrorResponseBody methodNotAllowedOf(Exception exception, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message, requestUri));
    }
    
    public static ErrorResponseBody of(ErrorType customErrorType, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(customErrorType.getErrorCode(), message, requestUri));
    }
    
    public static ErrorResponseBody of(Exception exception, Object message, String requestUri) {
        return new ErrorResponseBody(new ErrorResponse(exception.getClass().getSimpleName(), message, requestUri));
    }
    
    
    
    /* *********** Builder Methods *********** */
    
    public static MessageBuilder type(String type){
        return new MessageBuilder(type);
    }
    
    public static MessageBuilder type(ErrorType type){
        return new MessageBuilder(type.name());
    }
    
    public static MessageBuilder type(Exception exception) {
        return new MessageBuilder(exception.getClass().getSimpleName());
    }
    
    public String asJson() throws IOException {
        return objectMapper.writeValueAsString(this);
    }
    
    
    
    /* ********************** Getters for Jackson ********************** */
    public ErrorResponse getError() { return error; }
    
    @Override
    public String toString() {
        return "{\"ErrorResponseBody\":{" + "\"error\":" + error + "}}";
    }
    
    
    
    
    /* ********************** Builders ********************** */
    
    public static class MessageBuilder {
        private final String type;
    
        public MessageBuilder(String type) {
            this.type = type;
        }
    
        public RequestUriBuilder message(Object message){
            return new RequestUriBuilder(type, message);
        }
    
        public DetailsBuilder messageAnd(Object message){
            return new DetailsBuilder(type, message);
        }
    }
    
    public static class DetailsBuilder {
        private final String type;
        private final Object message;
    
        public DetailsBuilder(String type, Object message) {
            this.type = type;
            this.message = message;
        }
        
        public RequestUriBuilder details(Object details){
            return new RequestUriBuilder(type, message, details);
        }
    }
    
    public static class RequestUriBuilder{
        private final String type;
        private final Object message;
        private Object details;
        private String method;
    
        public RequestUriBuilder(String type, Object message) {
            this.type = type;
            this.message = message;
        }
    
        public RequestUriBuilder(String type, Object message, Object details) {
            this.type = type;
            this.message = message;
            this.details = details;
        }
        
        public RequestUriBuilder method(String method){
            this.method = method;
            return this;
        }
    
        public ErrorResponseBody requestUri(String requestUri){
            String requestUriWithMethod;
            
            if (method != null){
                requestUriWithMethod = method.toUpperCase(Locale.ROOT) + " " + requestUri;
            } else {
                requestUriWithMethod = requestUri;
            }
    
            if ( details != null ) {
                return new ErrorResponseBody(new ErrorResponse(type, message, details, requestUriWithMethod));
            }
            else {
                return new ErrorResponseBody(new ErrorResponse(type, message, requestUriWithMethod));
            }
        }
    }
}
