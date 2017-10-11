package com.colgorithmic.isqtbquestionbank.exception;

/**
 * Created by anandsoni on 2017-10-08.
 */
public class AnalyticsGenerationException extends RuntimeException {

    public String message;

    public AnalyticsGenerationException(String message){
          super(message);
        this.message=message;
    }


    public String toString(){
        return message;
    }
}
