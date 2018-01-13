package com.example.jay.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jay.quizapp.Database.DatabaseAccess;


/**
 * Created by jay on 12-Jan-18.
 */

public class ResultActivity extends AppCompatActivity {


    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        databaseAccess = DatabaseAccess.getInstance(this);

        TextView scoreTxtView = (TextView) findViewById(R.id.score);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
        ImageView img = (ImageView)findViewById(R.id.img1);



        Bundle b = getIntent().getExtras();
        final int score = b.getInt("score");
        ratingBar.setRating(score);
        scoreTxtView.setText(String.valueOf(score));

        TextView tv_fullscore = (TextView) findViewById(R.id.tv_fullscore);
        tv_fullscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,  FullResult.class);
                Bundle b = new Bundle();
                b.putInt("score", score);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        if(score <= 2){
            img.setImageResource(R.drawable.score_0);
        }else if(score > 2 && score <= 4){
            img.setImageResource(R.drawable.score_1);
        }else if( score > 4 && score <= 6){
            img.setImageResource(R.drawable.score_2);
        }else if( score >6 && score <= 7){
            img.setImageResource(R.drawable.score_3);
        }else if(score >7 && score <= 8){
            img.setImageResource(R.drawable.score_4);
        }else if(score >8 && score <= 10){
            img.setImageResource(R.drawable.score_5);
        }
    }

    @Override
    public void onBackPressed() {
        /*databaseAccess.open();
        databaseAccess.deleteData();
        databaseAccess.close();*/
        startActivity(new Intent(ResultActivity.this,MainActivity.class));
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
