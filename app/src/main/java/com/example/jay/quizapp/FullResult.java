package com.example.jay.quizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
public class FullResult extends AppCompatActivity {


    List<QuestionModel> questionModelList;
    int score = 0;
    int quid = 0;
    QuestionModel currentQuestionModel;

    TextView txtQuestion, givensnswer;
    RadioButton rda, rdb, rdc, rdd;
    Button butNext;
    DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        DbHelper dbHelper = new DbHelper(this);
        questionModelList = dbHelper.getAllQuestions();*/

        Bundle b = getIntent().getExtras();
        score = b.getInt("score");

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        questionModelList = databaseAccess.getAllQuestions();
        databaseAccess.close();

        //Collections.shuffle(questionModelList);
        currentQuestionModel = questionModelList.get(quid);

        txtQuestion = (TextView) findViewById(R.id.question);
        givensnswer = (TextView) findViewById(R.id.givensnswer);
        rda = (RadioButton) findViewById(R.id.radio0);
        rdb = (RadioButton) findViewById(R.id.radio1);
        rdc = (RadioButton) findViewById(R.id.radio2);
        rdd = (RadioButton) findViewById(R.id.radio3);
        butNext = (Button) findViewById(R.id.button1);
        setQuestionView();


    }

    private void setQuestionView() {
        RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroup1);
        grp.setEnabled(false);
       // RadioButton answer = (RadioButton)findViewById(grp.getCheckedRadioButtonId());

        txtQuestion.setText("QUE - " + (quid+1) + " ) " + currentQuestionModel.getQuestion());
        rda.setText(currentQuestionModel.getA());
        rdb.setText(currentQuestionModel.getB());
        rdc.setText(currentQuestionModel.getC());
        rdd.setText(currentQuestionModel.getD());

        if(rda.getText().toString().equalsIgnoreCase(currentQuestionModel.getAnswer().toString())){
            rda.setChecked(true);
        }else if(rdb.getText().toString().equalsIgnoreCase(currentQuestionModel.getAnswer().toString())){
            rdb.setChecked(true);
        }else if(rdc.getText().toString().equalsIgnoreCase(currentQuestionModel.getAnswer().toString())){
            rdc.setChecked(true);
        }else if(rdd.getText().toString().equalsIgnoreCase(currentQuestionModel.getAnswer().toString())){
            rdd.setChecked(true);
        }

        givensnswer.setText("Your Answer : "+currentQuestionModel.getGivenanswer());
        quid++;
    }

    public void btClick(View view) {


        if (quid < 10) {
            currentQuestionModel = questionModelList.get(quid);
            setQuestionView();
        } else {
            Intent intent = new Intent(FullResult.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FullResult.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}