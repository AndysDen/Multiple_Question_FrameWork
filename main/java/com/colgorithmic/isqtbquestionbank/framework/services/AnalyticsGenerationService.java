package com.colgorithmic.isqtbquestionbank.framework.services;

import android.content.Context;

import com.colgorithmic.isqtbquestionbank.exception.AnalyticsGenerationException;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.pojo.ResultHolder;
import com.colgorithmic.isqtbquestionbank.pojo.TestResults;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by anandsoni on 2017-10-08.
 */
public class AnalyticsGenerationService {

    static AnalyticsGenerationService analyticsGenerationService=null;

Context context=null;

    private AnalyticsGenerationService(Context context){
        this.context=context;
    }

    public static AnalyticsGenerationService getAnalyticGeneratorService(Context context){
        if(analyticsGenerationService==null){
            analyticsGenerationService = new AnalyticsGenerationService(context);
        }
    return analyticsGenerationService;
    }

    public ResultHolder populateData(TestResults testResults){
        ResultHolder resultHolder=readFile();

        if(resultHolder ==null){

            //this is first time scenario , hence we will create a file and save analytics
            resultHolder= new ResultHolder();


        }
        resultHolder.addTestResult(testResults);
        writeToFile(resultHolder);
        return resultHolder;

    }
    private void writeToFile(ResultHolder data) {
        try {
            ObjectOutputStream outputStreamWriter = new ObjectOutputStream(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.writeObject(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            new AnalyticsGenerationException(ApplicationConstants.ANALYTICS_IO_EXCEPTION);
        }
    }
    public   ResultHolder readFile(){
        ResultHolder resultHolder = null;

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                ObjectInputStream objectInputStream= new ObjectInputStream(inputStream);
                resultHolder=(ResultHolder)objectInputStream.readObject();
            }
        }
        catch (FileNotFoundException e) {
            new AnalyticsGenerationException(ApplicationConstants.ANALYTICS_FILE_NOT_FOUND);
        } catch (IOException e) {
            new AnalyticsGenerationException(ApplicationConstants.ANALYTICS_IO_EXCEPTION);
        }catch(ClassNotFoundException cnfe){
            new AnalyticsGenerationException(ApplicationConstants.ANALYTICS_CLASS_NOT_FOUND);
        }


        return resultHolder;
    }

}

