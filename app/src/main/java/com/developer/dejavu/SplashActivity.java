package com.developer.dejavu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 80;
    ProgressBar progressBar;
    private int i=0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressbar_splash);
        progressBar.setProgress(0);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i<SPLASH_TIME_OUT)
                {
                    progressBar.setProgress(i);
                    i+=1.5;
                }
                else
                {
                    timer.cancel();
                    finish();
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
            }
        },0,SPLASH_TIME_OUT);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
//                finish();
//                startActivity(homeIntent);
//            }
//        },SPLASH_TIME_OUT);

    }
}
