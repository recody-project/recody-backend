package com.recody.recodybackend.common.web;

class ErrorResponse {
    
    private String type;
    private Object message;
    private Object details;
    private String requestUri;
    
    public ErrorResponse(String type, Object message, String requestUri) {
        this.type = type;
        this.message = message;
        this.requestUri = requestUri;
    }
    
    public ErrorResponse(String type, Object message, Object details, String requestUri) {
        this.type = type;
        this.message = message;
        this.details = details;
        this.requestUri = requestUri;
    }
    
    /* ********************** Getters for Jackson ********************** */
    public String getType() { return type; }
    public Object getMessage() { return message; }
    public Object getDetails() {return details; }
    public String getRequestUri() { return requestUri; }
    
    @Override
    public String toString() {
        return "{\"ErrorResponse\":{" + "\"type\":" + ((type != null) ? ("\"" + type + "\"") : null) + ", \"message\":" + message + ", \"detail\":" + details + ", \"requestUri\":" + ((requestUri != null) ? ("\"" + requestUri + "\"") : null) + "}}";
    }
}
