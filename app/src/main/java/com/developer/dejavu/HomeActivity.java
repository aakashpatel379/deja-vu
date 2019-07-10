package com.developer.dejavu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin, btnHelp, btnSound, btnPlay, btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHelp=(Button)findViewById(R.id.btn_help);
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
            case R.id.btn_help:
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
        openDialog();
    }
    private void sound() {

    }
    private void play() {

    }
    private void quit() {
        onBackPressed();
    }
    private void login() {
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }
    public void openDialog(){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_help_popup, null);


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

}




