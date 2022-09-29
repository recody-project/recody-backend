package com.recody.recodybackend.common.web;

public class SuccessResponseBody {
    
    private String message;
    private Object data;
    
    public SuccessResponseBody(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    
    public SuccessResponseBody() {
    }
    
    public static Builder builder(){
        return new Builder();
    }
    
    public String getMessage() { return message; }
    
    public Object getData() { return data; }
    
    @Override
    public String toString() {
        return "{\"SuccessResponseBody\":{" + "\"message\":" + ((message != null) ? ("\"" + message + "\"") : null) + ", \"data\":" + data + "}}";
    }
    
    public static class Builder{
        private String message;
        private Object data;
        
        public Builder message(String message){
            this.message = message;
            return this;
        }
    
        public Builder data(Object data){
            this.data = data;
            return this;
        }
        
        public SuccessResponseBody build(){
            return new SuccessResponseBody(this.message, this.data);
        }
    }
}
