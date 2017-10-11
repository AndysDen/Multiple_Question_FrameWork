package com.colgorithmic.isqtbquestionbank.framework.services;

import com.colgorithmic.isqtbquestionbank.exception.RestFulServiceURLCreationException;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.helper.FrameWorkHelper;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anandsoni on 2017-10-08.
 * This class will gather user information and will send it accross to server for further
 * Analytics
 *
 */
public class UserAnalyticsService {
  FrameworkActivity frameworkActivity;

  public  UserAnalyticsService(FrameworkActivity frameworkActivity){
       this.frameworkActivity = frameworkActivity;

   }

    public void RegisterUserUsuage(){
        try {
            URL url = new URL(frameworkActivity.getApplicationContextData().getValue(ApplicationConstants.XML));
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
        } catch (MalformedURLException e) {
            throw new RestFulServiceURLCreationException(ApplicationConstants.USER_ANALYTICS_URL_CREATION_EXCEPTION);
        }
    }

    public String  getUserUniqueId(){
        String imeiNumber=FrameWorkHelper.geIMEINumber(frameworkActivity);
        return imeiNumber;
    }

}
