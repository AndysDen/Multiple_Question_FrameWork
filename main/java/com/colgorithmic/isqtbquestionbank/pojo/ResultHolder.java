package com.colgorithmic.isqtbquestionbank.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by anandsoni on 2016-05-17.
 */
public class ResultHolder implements Serializable {

    Set<TestResults> setOfTestResult;

    public void addTestResult(TestResults testResults){

         if(null== setOfTestResult){
        setOfTestResult = new TreeSet<TestResults>();

         }
        testResults.setTimeInmilliSeconds(Calendar.getInstance().getTimeInMillis());
        setOfTestResult.add(testResults);

    }

     public Set<TestResults> getSetOfTestResult(){
          return setOfTestResult;
     }
}
