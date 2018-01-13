package com.example.jay.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jay.quizapp.Database.DatabaseAccess;

import java.util.Collections;
import java.util.List;


/**
 * Created by jay on 12-Jan-18.
 */

public class MainActivity extends AppCompatActivity {

    List<QuestionModel> questionModelList;
    int score = 0;
    int quid = 0;
    QuestionModel currentQuestionModel;

    TextView txtQuestion,time;
    RadioButton rda,rdb,rdc,rdd;
    Button butNext;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        DbHelper dbHelper = new DbHelper(this);
        questionModelList = dbHelper.getAllQuestions();*/

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        questionModelList = databaseAccess.getAllQuestions();
        databaseAccess.close();

        //Collections.shuffle(questionModelList);
        currentQuestionModel = questionModelList.get(quid);

        txtQuestion = (TextView)findViewById(R.id.question);
        time = (TextView)findViewById(R.id.time);
        rda = (RadioButton)findViewById(R.id.radio0);
        rdb = (RadioButton)findViewById(R.id.radio1);
        rdc = (RadioButton)findViewById(R.id.radio2);
        rdd = (RadioButton)findViewById(R.id.radio3);
        butNext = (Button)findViewById(R.id.button1);
        setQuestionView();

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                time.setText("done!");

                databaseAccess.open();
                databaseAccess.updateData(quid + 1, "");
                databaseAccess.close();

                if(quid<10){
                    currentQuestionModel = questionModelList.get(quid);
                    setQuestionView();
                    countDownTimer.cancel();
                    countDownTimer.start();
                }else{
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score",score);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }

        };
        countDownTimer.start();
    }

    private void setQuestionView(){

        RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroup1);
        grp.clearCheck();
        txtQuestion.setText("QUE - "+(quid+1)+" ) "+currentQuestionModel.getQuestion());
        rda.setText(currentQuestionModel.getA());
        rdb.setText(currentQuestionModel.getB());
        rdc.setText(currentQuestionModel.getC());
        rdd.setText(currentQuestionModel.getD());
        quid++;
    }

    public void btClick(View view){
        RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton answer = (RadioButton)findViewById(grp.getCheckedRadioButtonId());

        if(grp.getCheckedRadioButtonId()==-1){
            databaseAccess.open();
            databaseAccess.updateData(quid + 1, "");
            databaseAccess.close();
        }else {
            databaseAccess.open();
            databaseAccess.updateData(quid + 1, answer.getText().toString());
            databaseAccess.close();
        }

        if(currentQuestionModel.getAnswer().equalsIgnoreCase(answer.getText().toString())){
            score++;
            Log.d("Score", "Your score: "+score);
        }

        if(quid<10){
            currentQuestionModel = questionModelList.get(quid);
            setQuestionView();

            countDownTimer.cancel();
            countDownTimer.start();
            
        }else{
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score",score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
