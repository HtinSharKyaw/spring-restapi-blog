package com.example.springblogrestapi.exception;


import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
    public BlogApiException(HttpStatus status,String message,String message1){
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }
}
