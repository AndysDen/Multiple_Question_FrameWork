package com.colgorithmic.isqtbquestionbank;

/*

This is landing screen of application. This class has following responsiblity :

a. Load the configuration for application.
b. Configure the landing screen.
take test.

This screen also contains instruction for the test takers.


created by Anand Soni

 */

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.colgorithmic.isqtbquestionbank.exception.ReadFailedException;
import com.colgorithmic.isqtbquestionbank.framework.ApplicationContextData;
import com.colgorithmic.isqtbquestionbank.helper.FrameWorkHelper;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkButton;


public class ExamQuestionBank extends FrameworkActivity {

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

   try {
       initalizeServices();
       //Validate if closed
       FrameWorkHelper.checkIfexitApplicationSelected(getIntent());
       //This method will initalize the application by reading the configuration of the project.
       initalizeApplication();
   }catch (ReadFailedException rfe){
       //printing the stack trace for debugging the application.
       rfe.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingscreen);

        configureTakeTestButton(context);
    }

    private void configureTakeTestButton(final Context context) {
        Button button = (Button)findViewById(R.id.next);
        FrameworkButton frameworkButton = new FrameworkButton(button,AllTestList.class,context);

    }

    private void initalizeApplication(){
         applicationContextData =new ApplicationContextData(this);
    }











}




