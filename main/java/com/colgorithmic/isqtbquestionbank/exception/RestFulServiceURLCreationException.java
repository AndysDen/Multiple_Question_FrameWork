package com.colgorithmic.isqtbquestionbank.exception;

/**
 * Created by anandsoni on 2017-10-09.
 */
public class RestFulServiceURLCreationException extends RuntimeException {

    private String message;

    public RestFulServiceURLCreationException(String message){
        super(message);
        this.message=message;
    }

    public String toString(){
        return message;
    }
}
