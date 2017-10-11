package com.colgorithmic.isqtbquestionbank.pojo;

import java.io.Serializable;

/**
 * Created by anandsoni on 2016-05-17.
 */
public class TestResults implements Serializable,Comparable<TestResults> {



    public int getNoOfCorrectAnswer() {
        return noOfCorrectAnswer;
    }

    public void setNoOfCorrectAnswer(int noOfCorrectAnswer) {
        this.noOfCorrectAnswer = noOfCorrectAnswer;
    }

    public int getNoOfIncorrectAnswer() {
        return noOfIncorrectAnswer;
    }

    public void setNoOfIncorrectAnswer(int noOfIncorrectAnswer) {
        this.noOfIncorrectAnswer = noOfIncorrectAnswer;
    }

    public int compareTo(TestResults testResults){
         int result =0;
         if(timeInmilliSeconds < testResults.timeInmilliSeconds){
     return -1;
         }
        return 1;
    }
    public long getTimeInmilliSeconds() {
        return timeInmilliSeconds;
    }

    public void setTimeInmilliSeconds(long timeInmilliSeconds) {
        this.timeInmilliSeconds = timeInmilliSeconds;
    }

    private int noOfIncorrectAnswer;
    private int noOfCorrectAnswer;



    private long timeInmilliSeconds;

}
