package com.colgorithmic.isqtbquestionbank.exception;

/**
 * Created by anandsoni on 2017-10-08.
 */
public class XMLFileLoadingException extends RuntimeException {

    private String message;

    public XMLFileLoadingException(String message){
        super(message);
        this.message=message;
    }

    public String toString(){

        return message;
    }

}
