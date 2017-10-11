package com.colgorithmic.isqtbquestionbank.exception;

/**
 * Created by anandsoni on 2017-10-07.
 */
public class ReadFailedException extends RuntimeException{

    private String message;

    public ReadFailedException(String message){
        super(message);
        this.message=message;

    }



}
