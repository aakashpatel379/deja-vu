package com.developer.dejavu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.developer.dejavu.util.SharedPrefHelper;

public class HomeActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = (Button) findViewById(R.id.playGame);
        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(getApplicationContext());
        if (!TextUtils.isEmpty(sharedPrefHelper.getString(CpuGame.USER_GAME_DATA, ""))) {
            findViewById(R.id.loadSavedGame).setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, CpuGame.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.playCpu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, CpuGame.class);
                intent.putExtra("mode", "cpu");
                startActivity(intent);
            }
        });
        findViewById(R.id.loadSavedGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, CpuGame.class);
                intent.putExtra("load_saved_game", true);
                startActivity(intent);
            }
        });
    }
}
