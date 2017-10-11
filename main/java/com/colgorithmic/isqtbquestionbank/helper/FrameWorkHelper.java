package com.colgorithmic.isqtbquestionbank.helper;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.colgorithmic.isqtbquestionbank.pojo.Questions;
import com.colgorithmic.isqtbquestionbank.pojo.TestResults;

import java.util.ArrayList;

/**
 * Created by anandsoni on 2017-10-07.
 */
public class FrameWorkHelper {


    public static void checkIfexitApplicationSelected(Intent intent) {
        if (intent.getBooleanExtra("EXIT", false)) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public static void killProcessByPID(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static void initalizeAndStartActivity(Context context,Class clazz){
        Intent i = new Intent(context, clazz);
        context.startActivity(i);
    }

    public static TestResults identifyCorrectIncorrectResult(ArrayList<Questions> arrList){
        TestResults correctIncorrect = new TestResults();
        for(final Questions question:arrList) {
            if (!question.getAttemptedAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
                correctIncorrect.setNoOfIncorrectAnswer(correctIncorrect.getNoOfIncorrectAnswer() + 1);

            } else {
                correctIncorrect.setNoOfCorrectAnswer(correctIncorrect.getNoOfCorrectAnswer() + 1);
            }
        }
        return correctIncorrect;
    }

    public static String geIMEINumber(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getImei();

    }

    public static String getClassName(String serviceClass, int i) {
        String[] arrString=serviceClass.split("\\.");
        return arrString[i];
    }
}
