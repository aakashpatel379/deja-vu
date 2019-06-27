package com.developer.dejavu;

import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class MainActivity extends AppCompatActivity {

    private Button mainButton;
    private Button leaderboard;
    private TextView scoreView;
    private TextView timeView;
    GoogleApiClient apiClient;

    private int score = 0;
    private boolean playing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .build();
        apiClient.connect();
        //Log.w("myApp", );
        //Toast.makeText(this,""+apiClient.getConnectionResult(Games.API),Toast.LENGTH_LONG);
        mainButton = (Button)findViewById(R.id.main_button);
        scoreView = (TextView)findViewById(R.id.score_view);
        timeView = (TextView)findViewById(R.id.time_view);
        leaderboard = (Button)findViewById(R.id.leaderboard_button);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // More code goes here
                if(!playing) {
                    // The first click
                    playing = true;
                    mainButton.setText("Keep Clicking");

                    // Initialize CountDownTimer to 60 seconds
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeView.setText("Time remaining: " + millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            playing = false;
                            timeView.setText("Game over");
                            mainButton.setVisibility(View.GONE);

                            Games.Leaderboards.submitScore(apiClient,
                                    getString(R.string.leaderboard_game_leaderboard),
                                    score);
                        }
                    }.start();  // Start the timer
                } else {
                    // Subsequent clicks
                    score++;
                    scoreView.setText("Score: " + score + " points");
                }
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeaderboard(v);
            }
        });


    }

    public void showLeaderboard(View v) {
        startActivityForResult(
                Games.Leaderboards.getLeaderboardIntent(apiClient,
                        getString(R.string.leaderboard_game_leaderboard)), 0);
    }

    public void showAchievements(View v) {
        startActivityForResult(
                Games.Achievements
                        .getAchievementsIntent(apiClient),
                1
        );
    }
}
