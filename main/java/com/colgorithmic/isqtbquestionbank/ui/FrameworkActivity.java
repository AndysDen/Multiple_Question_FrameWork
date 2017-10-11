package com.colgorithmic.isqtbquestionbank.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.colgorithmic.isqtbquestionbank.AboutTest;
import com.colgorithmic.isqtbquestionbank.ContactUs;
import com.colgorithmic.isqtbquestionbank.ProductInfo;
import com.colgorithmic.isqtbquestionbank.R;
import com.colgorithmic.isqtbquestionbank.framework.ApplicationContextData;
import com.colgorithmic.isqtbquestionbank.framework.services.QuestionLoadingService;
import com.colgorithmic.isqtbquestionbank.framework.services.ServiceLocator;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.helper.FrameWorkHelper;
import com.colgorithmic.isqtbquestionbank.pojo.Questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anandsoni on 2017-10-07.
 * This is super class for all the activities we have in the application.
 * The reason for this design is to encapsulate the application
 */
public abstract class FrameworkActivity extends AppCompatActivity {



 ServiceLocator serviceLocator;


    protected void initalizeServices() {
        //questionLoadingService=new QuestionLoadingService(this);
        serviceLocator=ServiceLocator.getServiceLocator(this);

    }

    protected static ApplicationContextData applicationContextData;

    protected  Map<String,List<Questions>> mapOfQuestions = new HashMap<String,List<Questions>>();


    public  ApplicationContextData getApplicationContextData() {
     if(null ==applicationContextData)
         applicationContextData= new ApplicationContextData(this);
        return applicationContextData;
    }

    public static void setApplicationContextData(ApplicationContextData applicationContextData) {
        FrameworkActivity.applicationContextData = applicationContextData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upper_right_menu_collection, menu);

        return true;
    }

    /*
This method is will capture the events when top right menu is clicked.
Each menu will kick off an Intent which will be used.
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.closeApplication) {
            FrameWorkHelper.killProcessByPID();
        }
        else if(id==R.id.faceBookUs){
            if(null==applicationContextData) {getApplicationContextData();}
            String url = applicationContextData.getValue(ApplicationConstants.FACEBOOK_URL);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        else if(id==R.id.aboutExam){
            FrameWorkHelper.initalizeAndStartActivity(this, AboutTest.class);
        }
        else if(id==R.id.contactUS){
            FrameWorkHelper.initalizeAndStartActivity(this, ContactUs.class);
        }
        else if(id==R.id.productInfo){
            FrameWorkHelper.initalizeAndStartActivity(this,ProductInfo.class);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is responsible to load the questions via XML

     */
    protected Map<String, List<Questions>> LoadQuestionForMap() {
        if(null==serviceLocator){
            initalizeServices();
        }
        if (mapOfQuestions.size() == 0) {
            QuestionLoadingService questionLoadingService=(QuestionLoadingService)serviceLocator.resolve(ApplicationConstants.QUESTION_LOADING_SERVICE);

            mapOfQuestions =questionLoadingService.loadQuestions();
        }
        return mapOfQuestions;
    }

        }


