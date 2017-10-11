package com.colgorithmic.isqtbquestionbank.framework;


import android.content.Context;
import android.content.res.AssetManager;

import com.colgorithmic.isqtbquestionbank.exception.ReadFailedException;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * Created by anandsoni on 2017-10-07.
 * This is a partially Immutable class which will be used to carry data application wide.
 * This class will carry two type of information :
 *
 * a. Once initialized this will read configuration file and load confirmation information.
 *
 * b. If any exception has occured then it will have information about exception.
 *
 *
 */
public class ApplicationContextData {

    private HashMap<String,String> mapOfConfiguration;
    private boolean hasExceptionOccured;
    private String contextMessage;
    private Context context;
    public ApplicationContextData(Context context) throws ReadFailedException{
        this.context = context;
        mapOfConfiguration = new HashMap<String,String>();
        loadConfiguration(context);
    }

    public boolean isExceptionOccured(){
        return hasExceptionOccured;

    }

    public void setHasExceptionOccured(){
        hasExceptionOccured=true;
    }

    public String getContextMessage(){
        return contextMessage;
    }
    public void setContextMessage(String message){
        contextMessage=message;
    }

    /**
     * This method is responsible for loading configuration files located in assets/config.properties.
     * This will contain the profile of the application which framework is responsible to load.
     */
    private void loadConfiguration(Context context) throws ReadFailedException{
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
             InputStream inputStream = assetManager.open(ApplicationConstants.CONFIG_FILE);
                properties.load(inputStream);

            Set<Object> setOfKeys=properties.keySet();

            for(Object key:setOfKeys){
                Object value =properties.get(key);
                mapOfConfiguration.put((String)key,(String)value);
            }

        }catch(IOException ioe){
            hasExceptionOccured=true;
            contextMessage=ApplicationConstants.CONFIG_FILE_READ_EXCEPTION;
            throw new ReadFailedException(ApplicationConstants.CONFIG_FILE_READ_EXCEPTION);


        }
    }

    public String getValue(String key){

        return mapOfConfiguration.get(key);
    }
}
