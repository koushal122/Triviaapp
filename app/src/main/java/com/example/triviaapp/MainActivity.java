package com.example.triviaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.ProcessCompleted;
import data.question;
import data.questionBank;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

     int highscore=0;
     int currentscore=0;
     Button sharebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharebutton=findViewById(R.id.sharebutton);

        sharedPreferences= getSharedPreferences("Score",MODE_PRIVATE);
        edit=sharedPreferences.edit();


        TextView highscoretext=(TextView)findViewById(R.id.HighestScore);
        TextView currentscoretext=findViewById(R.id.CurrentScore);
        SharedPreferences sh= getSharedPreferences("Score",MODE_PRIVATE);


        highscore=sh.getInt("highscore",0);
        highscoretext.setText("High Score :- "+highscore);

        currentscore=sh.getInt("currentscore",0);
        currentscoretext.setText("Current Score :- "+currentscore);

        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareintent=new Intent(Intent.ACTION_SEND);
//                shareintent.setType("text/plain");
//                shareintent.putExtra(Intent.EXTRA_SUBJECT,"I am playing Trivia game!");
//                shareintent.putExtra("CurrentScore","MY Current Score was : "+currentscore);
//                shareintent.putExtra("High Score","MY High Scorer was "+highscore);
//                try {
//                    startActivity(shareintent);
//                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "No activity found", Toast.LENGTH_SHORT).show();
//                }

                Uri number = Uri.parse("tel:5551234");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            }
        });



        final int[] i = {0};
        Button True=findViewById(R.id.Truebutton);

        Button False= (Button)findViewById(R.id.Falsebutton);
        ImageButton Prevois= (ImageButton)findViewById(R.id.Previous);
        TextView cardtext=(TextView)findViewById(R.id.Cardtext);

        ImageButton next=(ImageButton)findViewById(R.id.next);
        TextView count=findViewById(R.id.count);
        List<question> que = new questionBank().getQue(new ProcessCompleted() {
            @Override
            public void Processfinish (ArrayList<question> questons) {
                cardtext.setText(questons.get(i[0]).getQuestion());
                Log.d("jM", "OFFCreate:  "+questons);

               next.setOnClickListener(new View.OnClickListener() {
                   @SuppressLint("SetTextI18n")
                   @Override
                   public void onClick(View v) {
                       i[0]++;
                       count.setText(i[0]+" Out of "+questons.size());
                       cardtext.setText(questons.get(i[0]).getQuestion());
                   }
               });

                Prevois.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        if(i[0]!=0) {
                            i[0]--;
                            count.setText(i[0] + " Out of " + questons.size());
                            cardtext.setText(questons.get(i[0]).getQuestion());
                        }
                        else
                        {

                            Toast.makeText(MainActivity.this, "It is First", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

               False.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(!questons.get(i[0]).isAnswer())
                       {
                           Toast.makeText(MainActivity.this, "Wow ! You are right", Toast.LENGTH_SHORT).show();
                           currentscore++;
                           currentscoretext.setText("Current Score :- "+currentscore);
                       }
                       else {
                           Shakeanimation();
                           Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                           if(currentscore>0)
                               currentscore--;
                           currentscoretext.setText("Current Score :- "+currentscore);
                       }
                   }
               });

                True.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(questons.get(i[0]).isAnswer() == true)
                        {
                            Toast.makeText(MainActivity.this, "Wow ! You are right", Toast.LENGTH_SHORT).show();
                            currentscore++;
                            currentscoretext.setText("Current Score :- "+currentscore);
                        }
                        else {
                            Shakeanimation();
                            Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                            if(currentscore>0)
                                currentscore--;
                            currentscoretext.setText("Current Score :- "+currentscore);
                        }
                    }
                });




            }
        });

    }


    @Override
    protected void onPause() {


        edit.putInt("currentscore",currentscore);
        edit.apply();

        super.onPause();


    }

    @Override
    protected void onStop() {




        if(currentscore>highscore)
        {
          edit.putInt("highscore",currentscore);

          edit.apply();

        }
        edit.putInt("currentscore",0);
        edit.apply();

        super.onStop();
    }

    private void Shakeanimation() {
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        CardView card=(CardView)findViewById(R.id.cardView);
        card.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationStart(Animation animation) {
                card.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                card.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
               card.setCardBackgroundColor(Color.BLUE);
            }
        });
    }






}