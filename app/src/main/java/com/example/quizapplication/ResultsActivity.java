package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    private TextView scorecard,correctanswer,wrongans,totalAttempt;
    Button giveagain,solution;
    String title;
    public void checkSolutions(View view)
    {
        Intent intent=new Intent(ResultsActivity.this,SolutionsActivity.class);
        intent.putExtra("title",title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void giveAgain(View view)
    {
        Intent intent=new Intent(ResultsActivity.this,QuestionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title",title);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        scorecard=findViewById(R.id.scorecard);
        giveagain=findViewById(R.id.giveagain);
        solution=findViewById(R.id.solutions);
        correctanswer=findViewById(R.id.correct_ans);
        wrongans=findViewById(R.id.wrong_ans);
        totalAttempt=findViewById(R.id.ques_answered);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        String correctans=intent.getStringExtra("score");
        String totalques=intent.getStringExtra("totalques");
        String wronganswer=intent.getStringExtra("wrongans");
        String totalattempt=intent.getStringExtra("attempt");
        scorecard.setText(correctans+" out of "+ totalques);
        correctanswer.setText("No of Correct Answers:  "+correctans);
        wrongans.setText("No of Wrong Answers:  "+wronganswer);
        totalAttempt.setText("No of Questions Attempted:  "+totalattempt);



    }
}
