package com.developer.dejavu;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class CpuGame extends AppCompatActivity {

    ImageView curView = null;
    private int countpair = 0;
    int turnNow =1;

    final int[] clubs2 = new int[]{R.drawable.clubs1, R.drawable.clubs2, R.drawable.clubs3, R.drawable.clubs4,
            R.drawable.clubs5, R.drawable.clubs6, R.drawable.clubs7, R.drawable.clubs8,
            R.drawable.clubs9, R.drawable.clubs10, R.drawable.clubs11, R.drawable.clubs12, R.drawable.clubs13,R.drawable.clubs14};
    final int[] diamonds2 = new int[]{R.drawable.diamonds1, R.drawable.diamonds2, R.drawable.diamonds3, R.drawable.diamonds4,
            R.drawable.diamonds5, R.drawable.diamonds6, R.drawable.diamonds7, R.drawable.diamonds8,
            R.drawable.diamonds9, R.drawable.diamonds10, R.drawable.diamonds11, R.drawable.diamonds12, R.drawable.diamonds13,R.drawable.diamonds14};
    ArrayList<Integer> clubs = new ArrayList<>();
    ArrayList<Integer> diamonds = new ArrayList<>();
    TextView myPlayer;
    TextView cpu;
    TextView turn;
    Button quit;
    int player_score = 0;
    int cpu_score = 0;
    int match_found = 0;



    int currentPos = -1;
    boolean firstPickIsDiamond = false;
    boolean firstPickIsClub = false;
    int diamondPos;
    int secondPickPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_game);
        shuffle_cards();
        final GridView gridView = findViewById(R.id.gridView);
        myPlayer =  findViewById(R.id.player);
        cpu = findViewById(R.id.cpu);
        turn = findViewById(R.id.turn);
        quit = findViewById(R.id.quit);
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,final View view, int position, long id) {

                    if (currentPos < 0) {
                        if (position > 13) {
                            currentPos = position;
                            diamondPos = position - 14;
                            curView = (ImageView) view;
                            ((ImageView) view).setImageResource(diamonds.get(diamondPos));
                            firstPickIsDiamond = true;
                        } else {
                            currentPos = position;
                            curView = (ImageView) view;
                            ((ImageView) view).setImageResource(clubs.get(position));
                            firstPickIsClub = true;
                        }
                    } else {
                        if (position > 13) {
                            secondPickPosition = position - 14;
                            ((ImageView) view).setImageResource(diamonds.get(secondPickPosition));
                            if (firstPickIsClub) {
                                if (secondPickPosition == currentPos) {
                                    Toast.makeText(getApplicationContext(), "Match Found", Toast.LENGTH_SHORT).show();
                                    player_score++;
                                    match_found++;
                                    myPlayer.setText("Score: " + player_score);
                                    countpair++;
                                    view.setOnClickListener(null);
                                    curView.setOnClickListener(null);
//                                gridView.getChildAt(currentPos).setEnabled(false);
//                                gridView.getChildAt(secondPickPosition).setEnabled(false);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Match Not Found", Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ImageView) view).setImageResource(R.drawable.back);
                                            curView.setImageResource(R.drawable.back);

                                        }
                                    }, 2000);


                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Match Not Found", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ImageView) view).setImageResource(R.drawable.back);
                                        curView.setImageResource(R.drawable.back);
                                    }
                                }, 2000);

                            }
                        } else {
                            ((ImageView) view).setImageResource(clubs.get(position));
                            if (firstPickIsDiamond) {
                                if (position == diamondPos) {
                                    Toast.makeText(getApplicationContext(), "Match Found", Toast.LENGTH_SHORT).show();
                                    player_score++;
                                    myPlayer.setText("Score: " + player_score);
                                    view.setOnClickListener(null);
                                    curView.setOnClickListener(null);
                                    countpair++;

                                } else {
                                    Toast.makeText(getApplicationContext(), "Match Not Found", Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ImageView) view).setImageResource(R.drawable.back);
                                            curView.setImageResource(R.drawable.back);
                                        }
                                    }, 2000);

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Match Not Found", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ImageView) view).setImageResource(R.drawable.back);
                                        curView.setImageResource(R.drawable.back);
                                    }
                                }, 2000);

                            }
                        }
                        currentPos = -1;
                        firstPickIsDiamond = false;
                        firstPickIsClub = false;
                        turnNow = 0;
                        turn.setText("Player 1's turn ");
                    }
                }

        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CpuGame.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void shuffle_cards(){

        ArrayList<Integer> a  = new ArrayList<>();
        for(int i=0; i<14; i++) {
            a.add(new Integer(i));
        }
        Collections.shuffle(a);
        System.out.println(a);
        for(int j=0;j<a.size();j++){
            clubs.add(clubs2[a.get(j)]);
            diamonds.add(diamonds2[a.get(j)]);
        }


    }
}