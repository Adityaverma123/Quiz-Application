package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {
    Button next;
    TextView totalques,questions;
    RelativeLayout container;
    private int count=0;
    List<QuestionsModel>mQues;
    int position=0;
    int correctans=0;
    String subject="";
    DatabaseReference ref;
    long starttimer=120000;
    long timeLeftInMilli=starttimer;
    TextView clocktimer;
    CountDownTimer countDownTimer;
    Dialog loadingDialog;
    int wrongans=0;
    int attempt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loadingbar);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCanceledOnTouchOutside(false);
        next = findViewById(R.id.nextbutton);
        totalques = findViewById(R.id.totalquestions);
        questions = findViewById(R.id.questions);
        container = findViewById(R.id.secondlayout);
        clocktimer=findViewById(R.id.clocktimer);
        startTimer();
        mQues = new ArrayList<>();
        final Intent intent=getIntent();
        subject=intent.getStringExtra("title");
        loadingDialog.show();
        ref= FirebaseDatabase.getInstance().getReference().child("Tests").child(subject).child("questions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    QuestionsModel model=snapshot.getValue(QuestionsModel.class);
                    mQues.add(model);
                    if(mQues.size()>0)
                    {
                        for (int i = 0; i < 4; i++)
                        {
                            final int pos=i;
                            container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    attempt++;
                                    container.getChildAt(pos).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF03DAC5")));
                                    checkans((TextView) v);
                                }
                            });
                            playAnimation(questions,0,mQues.get(position).getQuestion());
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    next.setEnabled(false);
                                    enablebutton(true);

                                    position++;
                                    if(position==mQues.size())
                                    {
                                        Intent intent1=new Intent(QuestionsActivity.this,ResultsActivity.class);
                                        intent1.putExtra("score",String.valueOf(correctans));
                                        intent1.putExtra("totalques",String.valueOf(mQues.size()));
                                        intent1.putExtra("title",subject);
                                        intent1.putExtra("attempt",String.valueOf(attempt));
                                        intent1.putExtra("wrongans",String.valueOf(wrongans));
                                        startActivity(intent1);
                                        finish();

                                    }
                                    count=0;
                                    if(position<mQues.size()) {
                                        playAnimation(questions, 0, mQues.get(position).getQuestion());
                                    }

                                }
                            });
                        }
                    }
                    else
                    {
                        finish();
                        Toast.makeText(QuestionsActivity.this,"No Questions",Toast.LENGTH_SHORT).show();
                    }
                        loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });


    }


    public void playAnimation(final View view, final int value, final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                String options = "";
                if (value == 0 && count<4) {

                    if (count == 0) {
                        options = mQues.get(position).getOptionA();

                    } else if (count == 1) {
                        options = mQues.get(position).getOptionB();

                    } else if (count == 2) {
                        options = mQues.get(position).getOptionC();

                    } else if (count == 3) {
                        options = mQues.get(position).getOptionD();

                    }
                    playAnimation(container.getChildAt(count), 0, options);
                    count++;
                }
            }


            @Override
            public void onAnimationEnd(Animator animation) {
                if(value==0) {
                    try {
                        ((TextView) view).setText(data);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    playAnimation(view, 1,data);
                    view.setTag(data);
                    totalques.setText(position+1+"/"+mQues.size());

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkans(TextView selectedoption)
    {
        enablebutton(false);
        next.setEnabled(true);
        if(selectedoption.getText().toString().equals(mQues.get(position).getCorrectAns()))
        {
            correctans++;

        }
        else
        {
            wrongans++;
        }


    }
    public void enablebutton(boolean enable)
    {
        for(int i=0;i<4;i++)
        {
            container.getChildAt(i).setEnabled(enable);
            if(enable)
            {
                container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#27829C65")));
            }
        }
    }
    public void startTimer()
    {
        countDownTimer=new CountDownTimer(timeLeftInMilli,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilli=millisUntilFinished;
                int minutes=(int)(timeLeftInMilli/1000)/60;
                int seconds=(int)(timeLeftInMilli/1000)%60;
                String timeformat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                clocktimer.setText(timeformat);
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(QuestionsActivity.this,ResultsActivity.class);
                intent.putExtra("score",String.valueOf(correctans));
                intent.putExtra("totalques",String.valueOf(mQues.size()));
                intent.putExtra("title",subject);
                intent.putExtra("attempt",String.valueOf(attempt));
                intent.putExtra("wrongans",String.valueOf(wrongans));
                startActivity(intent);
                finish();
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
       if(countDownTimer!=null)
       {
           countDownTimer.cancel();
           countDownTimer=null;
       }

    }
}
