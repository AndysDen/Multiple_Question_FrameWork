package com.colgorithmic.isqtbquestionbank;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkButton;

import java.util.ArrayList;

public class AllTestList extends FrameworkActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        mapOfQuestions = LoadQuestionForMap();

        final Context context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.testpaperlist);


        Button testPaper1 = (Button) findViewById(R.id.test1);
        new FrameworkButton(testPaper1, TestPaper.class, context, ((ArrayList) mapOfQuestions.get("one")));

        Button testPaper2 = (Button) findViewById(R.id.test2);
        new FrameworkButton(testPaper2, TestPaper.class, context, ((ArrayList) mapOfQuestions.get("two")));

        Button testPaper3 = (Button) findViewById(R.id.test3);
        new FrameworkButton(testPaper3, TestPaper.class, context, ((ArrayList) mapOfQuestions.get("three")));

        Button testPaper4 = (Button) findViewById(R.id.test4);
        new FrameworkButton(testPaper4, TestPaper.class, context, ((ArrayList) mapOfQuestions.get("four")));

        Button testPaper5 = (Button) findViewById(R.id.test5);
        new FrameworkButton(testPaper4, TestPaper.class, context, ((ArrayList) mapOfQuestions.get("five")));


    }


}