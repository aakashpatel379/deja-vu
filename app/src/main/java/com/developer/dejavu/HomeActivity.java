package com.developer.dejavu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
   // For sound
    Button sound;
    static boolean soundOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = (Button) findViewById(R.id.playGame);
        sound = (Button) findViewById(R.id.volume);

        sound.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, CpuGame.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.volume){
            if(soundOn == true) {
                startService(new Intent(this, SoundService.class));
                soundOn = false;
            }
            else if(soundOn == false){
                stopService(new Intent(this, SoundService.class));
                soundOn = true;
            }

        }

    }
}
