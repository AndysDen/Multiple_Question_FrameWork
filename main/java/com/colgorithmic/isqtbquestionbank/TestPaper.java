package com.colgorithmic.isqtbquestionbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.colgorithmic.isqtbquestionbank.framework.services.AnalyticsGenerationService;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.helper.FrameWorkHelper;
import com.colgorithmic.isqtbquestionbank.pojo.Questions;
import com.colgorithmic.isqtbquestionbank.pojo.TestResults;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is one of the core classes for this application ;Following are the responsiblity of this class
 *
 *
 * a. Extract the information of timer from configuration file. [Minutes && Seconds]
 * b. Initialize the TimerTask and start this as separate thread: This manage timer displayed.
 * c. Responsible to initializing the the Reverse and Forward Stack
 */

public class TestPaper extends FrameworkActivity {
    private Handler handler = new Handler();
    private Runnable calculatetime = null;
    Answer answer = new Answer();
    private Context context = null;
    int currentQuestion = 0;
    private ArrayList < Questions > questionsList = new ArrayList < Questions > ();
    Stack < Questions > forwardStack = new Stack < Questions > ();
    Stack < Questions > reverseStack = new Stack < Questions > ();
    List < Answer > listOfAnswers = new ArrayList < Answer > ();
    HashMap < Integer, Integer > radioMap = new HashMap < Integer, Integer > ();
    public int minutes = Integer.parseInt(getApplicationContextData().getValue(ApplicationConstants.MINUTES));
    public int seconds = Integer.parseInt(getApplicationContextData().getValue(ApplicationConstants.SECONDS));
    public Timer timer = new Timer();
    TextView view = null;
    AnalyticsGenerationService analyticsGenerationService =  AnalyticsGenerationService.getAnalyticGeneratorService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final long startTime = System.currentTimeMillis();
        context = this;
        super.onCreate(savedInstanceState);
        questionsList = (ArrayList < Questions > ) getIntent().getSerializableExtra("testList");

        setContentView(R.layout.testtemplate);

        //trying to put a timer to run a clock
        view = (TextView) findViewById(R.id.clock);
        /***************************************
         * Starting a Thread
         *************************************/

        TimerTask timeTask = createTimerTask();
        if (savedInstanceState != null) {
            String value = savedInstanceState.getString("timerText");
            String arr[] = value.split(":");
            minutes = Integer.parseInt(arr[0]);
            seconds = Integer.parseInt(arr[1]);
            view.setText(savedInstanceState.getString("timerText"));
            forwardStack = (Stack < Questions > ) savedInstanceState.getSerializable("forward");
            reverseStack = (Stack < Questions > ) savedInstanceState.getSerializable("reverse");
            populateMap();
            populateScreen(forwardStack.peek());
            configureButtonsWithFunctions(context);
            timer = new Timer();

            timer.scheduleAtFixedRate(timeTask, 0, 1000);
        } else {
            populateMap();
            populateStatck(questionsList);
            populateScreen(forwardStack.peek());
            configureButtonsWithFunctions(context);
            timer.scheduleAtFixedRate(timeTask, 0, 1000);
        }




    }

    @NonNull
    private TimerTask createTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String stringSecond = null;
                        String stringMinutes = null;
                        if (seconds >= 10) {
                            stringSecond = String.valueOf(seconds);
                            //view.setText("0" + String.valueOf(minutes) + ":" + String.valueOf(seconds));
                        } else {
                            //view.setText("0" + String.valueOf(minutes) + ":0" + String.valueOf(seconds));
                            stringSecond = "0" + String.valueOf(seconds);
                        }
                        if (minutes >= 10) {
                            stringMinutes = String.valueOf(minutes);
                        } else {
                            stringMinutes = "0" + String.valueOf(minutes);
                        }

                        view.setText(stringMinutes + ":" + stringSecond);

                        seconds = seconds - 1;
                        if (seconds == 0) {
                            if (minutes == 0) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Time Up")
                                        .setMessage("Time Up,Exiting Test")
                                        .setPositiveButton("Check results", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                gotToResultScreen();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            minutes = minutes - 1;
                            seconds = 60;
                            if (minutes < 0) {
                                timer.cancel();
                                view.setText("00:00");
                            }

                        }
                    }
                });
            }
        };
    }

    public void populateMap() {

        RadioButton radioButton = (RadioButton) findViewById(R.id.one);
        radioMap.put(radioButton.getId(), 1);
        radioButton = (RadioButton) findViewById(R.id.two);
        radioMap.put(radioButton.getId(), 2);
        radioButton = (RadioButton) findViewById(R.id.three);
        radioMap.put(radioButton.getId(), 3);
        radioButton = (RadioButton) findViewById(R.id.four);
        radioMap.put(radioButton.getId(), 4);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR &&
                keyCode == KeyEvent.KEYCODE_BACK &&
                event.getRepeatCount() == 0) {

            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(context)
                .setTitle("Exit Test")
                .setMessage("Are you sure you want to exit this  test ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        timer.cancel();
                        Intent intent = new Intent(context, AllTestList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return;
    }
    /*
     * This method will directly take us to result screen.
     */
    public void gotToResultScreen() {
        ArrayList < Questions > arrList = new ArrayList < Questions > ();
        while (!reverseStack.empty()) {
            forwardStack.push(reverseStack.pop());
        }
        while (!forwardStack.empty()) {
            arrList.add(forwardStack.pop());
        }



        Intent intent = new Intent(context, Results.class);
        intent.putExtra("list", arrList);
        startActivity(intent);
    }
    private void configureButtonsWithFunctions(final Context context) {

        Button nextButton = (Button) findViewById(R.id.testNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
                if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                    Toast.makeText(context, "Please Select Option", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!forwardStack.empty()) {
                    Questions question = forwardStack.pop();
                    radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
                    Integer attemptedAnswer = radioMap.get(radioGroup.getCheckedRadioButtonId());
                    question.setAttemptedAnswer("" + attemptedAnswer);
                    reverseStack.push(question);
                    if (!forwardStack.empty()) {
                        populateScreen(forwardStack.peek());
                        if (forwardStack.peek().getAttemptedAnswer().equals("Not Attempted")) {
                            radioGroup.clearCheck();

                        } else {
                            radioGroup.check(getValueFromMap(question.getAttemptedAnswer()));
                        }
                    } else {
                        ArrayList < Questions > arrList = new ArrayList < Questions > ();
                        while (!reverseStack.empty()) {
                            forwardStack.push(reverseStack.pop());
                        }
                        while (!forwardStack.empty()) {
                            arrList.add(forwardStack.pop());
                        }
                        //All the question has been travessered hence we will now generate data for analytics.
                        TestResults testResults=FrameWorkHelper.identifyCorrectIncorrectResult(arrList);
                        analyticsGenerationService.populateData(testResults);

                        Intent intent = new Intent(context, Results.class);
                        intent.putExtra("list", arrList);
                        startActivity(intent);
                    }

                }

            }
        });

        Button backButton = (Button) findViewById(R.id.testBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reverseStack.empty()) {
                    Questions question = reverseStack.pop();
                    populateScreen(question);
                    forwardStack.push(question);
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
                    if (!question.getAttemptedAnswer().equals("Not Attempted"))
                        radioGroup.check(getValueFromMap(question.getAttemptedAnswer()));

                }
            }
        });

        Button goToQuestionButton = (Button) findViewById(R.id.goToQuestionButton);
        goToQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultCode = 0;
                Intent intent = new Intent(context, choose_question.class);
                startActivityForResult(intent, resultCode);
            }
        });
    }
    /**
     * Below methods will be called when user will choose question at randon from choose
     * question screen. This method has following responsibility :
     * *************************************************************
     * a. extract the value send as part of Indent request.
     * b. Extract the question from dataStructure and populateScreen.
     * ******************************************************************
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String questionNumber = data.getStringExtra("value");
                int qNumber = Integer.parseInt(questionNumber);
                Questions question = identifyQuestion(qNumber);
                populateScreen(question);
            }
        }
    }

    private Questions identifyQuestion(int qNumber) {
        if (qNumber > forwardStack.peek().getQuestionNumber()) {
            int jumpCount = qNumber - forwardStack.peek().getQuestionNumber();
            for (int i = 0; i < jumpCount; i++) {
                Questions question = forwardStack.pop();
                reverseStack.push(question);
            }

        } else if (qNumber < forwardStack.peek().getQuestionNumber()) {
            int jumpCount = forwardStack.peek().getQuestionNumber() - qNumber;
            for (int i = 0; i < jumpCount; i++) {
                Questions question = reverseStack.pop();
                forwardStack.push(question);
            }

        }
        return forwardStack.peek();
    }

    private void populateStatck(ArrayList < Questions > questionsList) {
        int questionNumber = questionsList.size();
        for (Questions question: questionsList) {
            question.setQuestionNumber(questionNumber);
            forwardStack.push(question);
            questionNumber--;
        }
    }

    /**
     *
     * @param question
     * This method is responsible for populating the screen with details of question passed
     * as parameter to this method.
     */

    private void populateScreen(Questions question) {

        TextView questionText = (TextView) findViewById(R.id.textQuestion);
        questionText.setText(question.getQuestionNumber() + " " + question.getQuestion());
        populateRadioButton(question);
        answer.setCorrectAnswer(Integer.parseInt(question.getCorrectAnswer()));
    }

    /**
     *
     * @param question
     * This method has following two responsiblity :
     * a. Populate the radio button with the options of the questions passed as parameter.
     * b. If the user is re-visting the question answered previously in session then
     *    what is the option user has selected.
     */


    private void populateRadioButton(Questions question) {

        //First Radio button
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        radioGroup.clearCheck();

        RadioButton radio1 = (RadioButton) findViewById(R.id.one);
        radio1.setText(question.getListOfOption().get(0));

        //Second Radio Button
        RadioButton radio2 = (RadioButton) findViewById(R.id.two);
        radio2.setText(question.getListOfOption().get(1));
        //Third Radio Button
        RadioButton radio3 = (RadioButton) findViewById(R.id.three);
        radio3.setText(question.getListOfOption().get(2));

        RadioButton radio4 = (RadioButton) findViewById(R.id.four);
        radio4.setText(question.getListOfOption().get(3));
        if (!(question.getAttemptedAnswer().equals("Not Attempted"))) {
            Integer radioButton = 0;
            radioButton = getValueFromMap(question.getAttemptedAnswer());
            radioGroup.check(radioButton);
        }
    }



    private Integer getValueFromMap(String attemptedAnswer) {
        Integer value = 0;
        for (Map.Entry < Integer, Integer > entry: radioMap.entrySet()) {
            if (Integer.parseInt(attemptedAnswer) == entry.getValue().intValue()) {
                value = entry.getKey();
                break;
            }


        }

        return value;
    }

    /**
     *
     * @param outState
     * This method is overridden and is called when moving from one question to another.
     * This method saves following fields :
     * a. TimerText : This contains how much is the time currently spend.
     * b. Forward Stack : This dataStructure contains question user yet to encounter.
     * c. Reverse Stack : This dataStructure contains question which user has already answered.
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // timer.cancel();
        outState.putString("timerText", view.getText().toString());
        outState.putSerializable("forward", forwardStack);
        outState.putSerializable("reverse", reverseStack);
    }

    /*
       This is default method but overrriden for this activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_menu_file, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.faceBookUs) {
            String url = applicationContextData.getValue(ApplicationConstants.FACEBOOK_URL);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.aboutExam) {
            FrameWorkHelper.initalizeAndStartActivity(this, AboutTest.class);
        } else if (id == R.id.contactUS) {
            FrameWorkHelper.initalizeAndStartActivity(this, ContactUs.class);
        } else if (id == R.id.productInfo) {
            FrameWorkHelper.initalizeAndStartActivity(this, ProductInfo.class);
        }
        return super.onOptionsItemSelected(item);
    }

}