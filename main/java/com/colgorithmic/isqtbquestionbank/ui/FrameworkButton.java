package com.colgorithmic.isqtbquestionbank.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.colgorithmic.isqtbquestionbank.pojo.Questions;

import java.util.ArrayList;

/**
 * Created by anandsoni on 2017-10-06.
 */
public class FrameworkButton {

    Button button;
    Context context;

    public FrameworkButton(Button button, Class value,Context context){
        this.button=button;
        this.context=context;
        initalizeButton(value,null);
    }



    private void initalizeButton(final Class value, final ArrayList<Questions> listOfQuestions){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, value);
                if (null != listOfQuestions) {
                    intent.putExtra("testList", listOfQuestions);
                }
                context.startActivity(intent);
            }
        });
    }

    public FrameworkButton(Button button, Class value,Context context,ArrayList<Questions> listOfQuestions){

        this.button=button;
        this.context=context;
        initalizeButton(value,listOfQuestions);


    }

}
