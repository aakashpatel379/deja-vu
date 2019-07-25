package com.developer.dejavu.display;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.dejavu.R;
import com.developer.dejavu.service.SoundService;

/**
 * Main Home Activity to help user play without logging in along with being able to provide access to other features.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin, btnHelp, btnSound, btnPlay, btnQuit;
    private static int VOL_TIME_OUT = 1700;
    private static Boolean volumeDisabled=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHelp=(Button)findViewById(R.id.btn_play_guide);
        btnLogin =(Button)findViewById(R.id.btn_login);
        btnPlay=(Button)findViewById(R.id.btn_play);
        btnQuit=findViewById(R.id.btn_quit);
        btnSound=findViewById(R.id.btn_sound);
        btnSound.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnHelp.setOnClickListener(this);

    }

    /**
     * Handles event when one preses back button.
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quit")
                .setMessage("Are you sure you want quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_play_guide:
                help();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_play:
                play();
                break;
            case R.id.btn_sound:
                sound();
                break;
            case R.id.btn_quit:
                quit();
                break;
        }

    }

    private void help() {
        playGuide();
    }
    private void sound() {
        volumeDialog();
    }
    private void play() {
        Intent intent =new Intent(HomeActivity.this, CpuGameActivity.class);
        intent.putExtra("mode", "cpu");
        startActivity(intent);
    }
    private void quit()
    {
        onBackPressed();
    }

    private void login() {

        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void volumeDialog()
    {
        View volView = HomeActivity.this.getLayoutInflater().inflate(R.layout.layout_volume, null);
        final Dialog volDialog =new Dialog(HomeActivity.this);
        volDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        volDialog.show();
        volDialog.setCanceledOnTouchOutside(false);
        volDialog.setContentView(volView);
        ImageView ivVolumeOff =(ImageView) volView.findViewById(R.id.iv_volume_off);
        ImageView ivVolumeOn = (ImageView)volView.findViewById(R.id.iv_volume_on);
        if(volumeDisabled)
        {
            ivVolumeOff.setVisibility(View.INVISIBLE);
            ivVolumeOn.setVisibility(View.VISIBLE);
            startService(new Intent(this, SoundService.class));
            volumeDisabled=false;
        }
        else
        {
            ivVolumeOff.setVisibility(View.VISIBLE);
            ivVolumeOn.setVisibility(View.INVISIBLE);
            stopService(new Intent(this, SoundService.class));
            volumeDisabled=true;
        }
        createDelay(volDialog);
    }

    /**
     * Method to open Help for User.
     * It basically enlists the rules of the game.
     */
    public void playGuide(){
        View dialogView = HomeActivity.this.getLayoutInflater().inflate(R.layout.layout_help_popup, null);
        final Dialog mainDialog =new Dialog(HomeActivity.this);
        mainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainDialog.show();
        mainDialog.setCanceledOnTouchOutside(false);
        mainDialog.setContentView(dialogView);
        ImageView ivCloseSymbol =(ImageView) dialogView.findViewById(R.id.iv_close);
        Button closeButton = (Button) dialogView.findViewById(R.id.btn_Close);
        ivCloseSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDialog.dismiss();

            }
        });

    }

    public void createDelay(final Dialog volDialog)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                volDialog.dismiss();
            }
        },VOL_TIME_OUT);
    }

}




