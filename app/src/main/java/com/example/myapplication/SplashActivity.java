package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;

    ConstraintLayout background,back,image;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      /*  background = findViewById(R.id.background);
        image = findViewById(R.id.image);
        back = findViewById(R.id.back);
        image.setAlpha(0);
        image.setTranslationY(-40);
        image.animate().alpha(1).setDuration(200);
        image.animate().translationY(0).setDuration(1000).setStartDelay(100);

        back.animate().alpha(0).setDuration(800).setStartDelay(500);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this,MainPromptActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
