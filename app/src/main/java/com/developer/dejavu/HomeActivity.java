package com.developer.dejavu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = (Button) findViewById(R.id.playGame);
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
                startActivity(intent);
            }
        });
    }
}
