package com.colgorithmic.isqtbquestionbank.exception;

/**
 * Created by anandsoni on 2017-10-08.
 */
public class XMLParsingException extends RuntimeException {

    private String message;

    public XMLParsingException(String message){
        super(message);
        this.message=message;
    }

    public String toString(){
         return message;
    }
}
