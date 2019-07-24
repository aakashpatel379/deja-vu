package com.developer.dejavu.display;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.util.ArraySet;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.dejavu.model.Guest;
import com.developer.dejavu.R;
import com.developer.dejavu.adapter.CardImageAdapter;
import com.developer.dejavu.cpu.Cpu;
import com.developer.dejavu.cpu.OnItemClickListener;
import com.developer.dejavu.gameplay.Card;
import com.developer.dejavu.gameplay.CardDB;
import com.developer.dejavu.gameplay.Game;
import com.developer.dejavu.gameplay.GameView;
import com.developer.dejavu.gameplay.PlayerMoveCallback;
import com.developer.dejavu.gameplay.UserGameData;
import com.developer.dejavu.util.PlayerGson;
import com.developer.dejavu.util.SharedPrefHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CpuGameActivity extends AppCompatActivity implements OnItemClickListener, GameView, SensorEventListener {

    ImageView curView = null;
    ArrayList<Card> clubs = new ArrayList<>();
    ArrayList<Card> diamonds = new ArrayList<>();
    TextView player1Score;
    TextView player2Score;
    TextView turn;
    private RecyclerView gridView;
    private CardImageAdapter imageAdapter;
    Button quit;
    int currentRound = 0;
    private PlayerMoveCallback playerMoveCallback;
    private Game game;
    private SharedPrefHelper sharedPrefHelper;
    public static String USER_GAME_DATA = "user_game_data";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static int SHAKE_THRESHOLD = 3;
    private boolean isAccelerometerTriggered = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_game);
        sharedPrefHelper = new SharedPrefHelper(getApplicationContext());

        init();

        if (getIntent().getBooleanExtra("load_saved_game", false)) {
            final UserGameData userGameData = new PlayerGson().getPlayerGson().fromJson(sharedPrefHelper.getString(USER_GAME_DATA, ""), UserGameData.class);
            if (userGameData != null) {
                imageAdapter.setCards(userGameData.getCards());

                gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        game.restoreGameData(userGameData.getGameData());
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }

        game.start();
    }

    private void init() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gridView = findViewById(R.id.cards);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 8);
        gridView.setLayoutManager(gridLayoutManager);
        gridView.addItemDecoration(new SpacesItemDecoration(5));

        player1Score = (TextView) findViewById(R.id.player1_score);
        player2Score = (TextView) findViewById(R.id.player2_score);
        turn = (TextView) findViewById(R.id.turn);
        quit = (Button) findViewById(R.id.quit);
        imageAdapter = new CardImageAdapter(this, shuffle_cards());
        gridView.setAdapter(imageAdapter);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent().hasExtra("mode") && getIntent().getStringExtra("mode").equals("cpu")) {
            game = new Game(getApplicationContext(), new Guest("Guest"), new Cpu(), this);
        } else {
            game = new Game(getApplicationContext(), new Guest("Guest"), new Guest("Guest2"), this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public Card getCard(int position) {
        return imageAdapter.getCard(position);
    }

    @Override
    public int getCardIndex(Card card) {
        return imageAdapter.getCards().indexOf(card);
    }

    @Override
    public void disableClickListeners() {
        imageAdapter.setOnItemClickListener(null);
    }

    @Override
    public void enableClickListeners() {
        imageAdapter.setOnItemClickListener(this);
    }

    @Override
    public void flipCard(final View from, @DrawableRes final int imageRes) {
        Animator animatorSet = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_to_y);
        animatorSet.setTarget(from);
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ImageView) from).setImageResource(imageRes);
                Animator animator = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_half_y_to_x);
                animator.setTarget(from);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void flipCardBack(final View from, @DrawableRes final int imageRes, final Callback<Void> callback) {
        Animator animatorSet = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_to_y_rev);
        animatorSet.setTarget(from);
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ImageView) from).setImageResource(imageRes);
                Animator animator = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_half_y_to_x_rev);
                animator.setTarget(from);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        callback.onCall();
                        currentRound = -1;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {                    }
                });
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {            }

            @Override
            public void onAnimationRepeat(Animator animation) {            }
        });
        animatorSet.start();
    }

    @Override
    public int cardCount() {
        return imageAdapter.getItemCount();
    }

    @Override
    public View getCardView(int position) {
        return gridView.getChildAt(position);
    }

    @Override
    public void removeCards(int idx1, int idx2) {
        imageAdapter.remove(idx1, idx2);
    }

    @Override
    public void pairMatched(Pair<Card, Card> cardPair) {
        //TODO: Show animation
    }

    @Override
    public void scoreUpdated(int p1, int p2) {
        player1Score.setText("Score: "+p1);
        player2Score.setText("Score: "+p2);
    }

    @Override
    public void turnChanged(String playerName) {
        turn.setText(playerName+"' Turn");
    }

    @Override
    public void gameFinished(String winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Match finished");
        builder.setMessage(winner + " Won");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void onPlayerMove(PlayerMoveCallback playerMoveCallback) {
        this.playerMoveCallback = playerMoveCallback;
    }

    @Override
    public void disableCard(int position) {
        gridView.getChildAt(position).setClickable(false);
    }

    @Override
    public void enableCard(int position) {
        gridView.getChildAt(position).setClickable(true);
    }

    public ArraySet<Card> shuffle_cards(){
        ArrayList<Card> a  = new ArrayList<>();
        a.addAll(Arrays.asList(CardDB.getInstance().clubs));
        a.addAll(Arrays.asList(CardDB.getInstance().diamonds));
        Collections.shuffle(a);
        ArraySet<Card> cards = new ArraySet<>();
        cards.addAll(a);
        return cards;
    }

    @Override
    public void onItemClick(View v, int position) {
        playerMoveCallback.onPlayerMove(v, position);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;

        if (acceleration > SHAKE_THRESHOLD && isAccelerometerTriggered) {
            createNewGame();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space = 5;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = space;
            }
        }
    }

    private void saveGame() {
        sharedPrefHelper.putString(USER_GAME_DATA, new PlayerGson().getPlayerGson().toJson(new UserGameData(game.getGameData(), imageAdapter.getCards())));
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Game");
        builder.setMessage("Game is not finished.\nPlease save before exit.");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.stop();
                dialog.dismiss();
                saveGame();
            }
        });
        builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.stop();
                dialog.dismiss();
                CpuGameActivity.super.onBackPressed();
            }
        });
        builder.create().show();
    }
    public void createNewGame(){
        isAccelerometerTriggered = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Restart Game");
        builder.setMessage("Game is not finished.\nRestart Game?");

        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent =new Intent(CpuGameActivity.this, CpuGameActivity.class);
                finish();
                startActivity(intent);

            }

        });
        builder.setNegativeButton("Resume Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isAccelerometerTriggered = true;
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}