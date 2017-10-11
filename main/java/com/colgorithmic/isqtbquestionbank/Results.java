package com.colgorithmic.isqtbquestionbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colgorithmic.isqtbquestionbank.pojo.Questions;
import com.colgorithmic.isqtbquestionbank.pojo.ResultHolder;
import com.colgorithmic.isqtbquestionbank.pojo.TestResults;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*



 */
public class Results extends FrameworkActivity {

    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        context=this;
        Intent intent = getIntent();
        ArrayList<Questions> arrList = (ArrayList<Questions>) intent.getSerializableExtra("list");

        LinearLayout mainPanel = (LinearLayout) findViewById(R.id.mainpanel);

         final TestResults results = new TestResults();

         for(final Questions question:arrList) {

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams  layoutParams=  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

             linearLayout.setLayoutParams(layoutParams);
             linearLayout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(context, ShowQuestionResult.class);
                     Bundle bundle = new Bundle();
                     bundle.putSerializable("question", question);
                     intent.putExtras(bundle);
                     startActivity(intent);
                 }
             });
             linearLayout.setOrientation(LinearLayout.VERTICAL);
             prepareLinerLayout(linearLayout, question);
             mainPanel.addView(linearLayout);
             linearLayout.setBackgroundResource(R.drawable.topbottom);
         }


        Button analytics=(Button)findViewById(R.id.checkPerformance);
         analytics.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 drawCharts();
             }
         });

    }

    private ResultHolder  populateData(TestResults results){
        ResultHolder resultHolder=readFile();

        if(resultHolder ==null){

            //this is first time scenario , hence we will create a file and save analytics
            resultHolder= new ResultHolder();


        }
        resultHolder.addTestResult(results);
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
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private  void drawCharts(){

        Intent chartIntent = new Intent(context,BarCharts.class);
        startActivity(chartIntent);

    }

    private  ResultHolder readFile(){
        ResultHolder resultHolder = null;

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                ObjectInputStream objectInputStream= new ObjectInputStream(inputStream);
                resultHolder=(ResultHolder)objectInputStream.readObject();




            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }catch(ClassNotFoundException cnfe){
            Log.e("login activity", "Class Not Found: " + cnfe.toString());
        }


        return resultHolder;
    }

    private void prepareLinerLayout(LinearLayout linearLayout,Questions question){
        TextView textView = new TextView(this);
         textView.setText("Question " + question.getQuestionNumber());
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setMargins(30, 30, 30, 30);
         textView.setPadding(10, 10, 10, 10);
        textView.setTextColor(Color.BLACK);

       textView.setLayoutParams(layout);
        textView.setTextSize(20);

        linearLayout.addView(textView);
        prepareCorrectorIncorrect(linearLayout, question, layout);
        prepareCorrectAnswer(linearLayout,question);

    }

    private void prepareCorrectAnswer(LinearLayout linearLayout, Questions question) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(70, 20,30,30);
        textView.setPadding(10, 10, 10, 10);
        textView.setTextSize(20);
        textView.setLayoutParams(layout);
        textView.setText(question.getCorrectAnswer() + " | " + question.getAttemptedAnswer()
        );
        textView.setTextColor(Color.BLACK);
        linearLayout.addView(textView);
    }

    private void prepareCorrectorIncorrect(LinearLayout linearLayout,Questions question,LinearLayout.LayoutParams layoutParams){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(60, 20, 30, 30);
        textView.setPadding(10, 10, 10, 10);
         textView.setLayoutParams(layout);
        if(question.getAttemptedAnswer().equals(question.getCorrectAnswer())) {
            textView.setBackgroundResource(R.mipmap.correct_answer);
        }else {
            textView.setBackgroundResource(R.mipmap.incorrect_answer);
        }

        linearLayout.addView(textView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(context)
                .setTitle("Exit Results")
                .setMessage(" What Next?")
                .setPositiveButton("Another Test", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,AllTestList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Exit Application", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(context,SubCategory.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        homeIntent.putExtra("EXIT", true);
                        startActivity(homeIntent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_menu_file, menu);

        return true;
    }



}
