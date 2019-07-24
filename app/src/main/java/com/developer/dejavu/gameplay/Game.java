package com.developer.dejavu.gameplay;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.developer.dejavu.Callback;
import com.developer.dejavu.Guest;
import com.developer.dejavu.R;
import com.developer.dejavu.cpu.Cpu;
import com.developer.dejavu.cpu.Player;
import com.developer.dejavu.cpu.PlayerType;

public class Game implements PlayerMoveCallback {
    private Context context;
    private Pair<Player, Player> opponents;
    private Player currentPlayer;
    private GameView gameView;
    private int currentPos = -1;
    private int secondPickPosition;
    private int currentRound = 0;
    private int nexRound = 1;
    private Handler handler;
    private boolean isRunning;

    private View firstCardView;
    long cpuTurnWaitTime = 2000; // 2Sec

    private Runnable cpuTurn = new Runnable() {
        @Override
        public void run() {
            Cpu cpuPlayer = ((Cpu) opponents.second);
            String key = null;
            Card card = null;
            if ((key = cpuPlayer.hasPair()) != null) {
                if (nexRound == 1) {
                    card = Card.parse(cpuPlayer.getCardKey(key, 0));
                } else {
                    String tmp_key = cpuPlayer.getCardKey(key, 1);
                    if (tmp_key.equals(gameView.getCard(currentPos).toString())) {
                        card = Card.parse(cpuPlayer.getCardKey(key, 0));
                    } else {
                        card = Card.parse(tmp_key);
                    }
                }
                if (card != null) {
                    int index = gameView.getCardIndex(card);
                    if (index > -1) {
                        playerMove(gameView.getCardView(index), index);
                    } else {
                        Log.e("Cpu game", "Card not found: "+card);
                    }
                }
            } else {
                int index = cpuPlayer.turn(currentPos, nexRound, gameView.cardCount());
                playerMove(gameView.getCardView(index), index);
            }
        }
    };

    public Game(Context context, Player p1, Player p2, GameView gameView) {
        this.opponents = new Pair<>(p1, p2);
        this.gameView = gameView;
        this.context = context;
        gameView.onPlayerMove(this);
    }

    public void start() {
        if (currentPlayer == null)
            currentPlayer = opponents.first;
        isRunning = true;
        currentPlayer.setPlaying(true);
        turn(currentPlayer, 1);
    }

    public void restoreGameData(GameData gameData) {
        opponents = new Pair<>(gameData.getP1(), gameData.getP2());
        currentPos = gameData.getCurrentPosition();

        if (currentPos > -1) {
            firstCardView = gameView.getCardView(currentPos);
            gameView.flipCard(firstCardView, gameView.getCard(currentPos).getCardImage());
        }
        if (gameData.getP1().isPlaying()) {
            currentPlayer = gameData.getP1();
        } else {
            currentPlayer = gameData.getP2();
        }
    }

    public GameData getGameData() {
        return new GameData(opponents.first, opponents.second, currentPos);
    }

    public void stop() {
        if (handler != null)
            handler.removeCallbacks(cpuTurn);
        isRunning = false;
    }

    private void playerMove(final View view, final int position) {
        Card card = gameView.getCard(position);

        addInCpu(card.getCardName(), card.toString());

        if(currentPos <0 ){
            currentRound = 1;
            nexRound = 2;
            currentPos = position;
            firstCardView = (ImageView) view;
            gameView.flipCard(view, card.getCardImage());
            turn(currentPlayer, 1);
            gameView.disableCard(position);
        }
        else if (currentPos != position){
            currentRound = 2;
            nexRound = 1;
            secondPickPosition = position;
            gameView.flipCard(view, card.getCardImage());
            gameView.disableClickListeners();
            gameView.enableCard(currentPos);
            gameView.enableCard(position);
            if (card.getCardName().equals(gameView.getCard(currentPos).getCardName())) {
                Toast.makeText(context, "Match Found", Toast.LENGTH_SHORT).show();
                pairMatched(currentPos, secondPickPosition);
            } else {
                Toast.makeText(context, "Match Not Found", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameView.flipCardBack(view, R.drawable.back, new Callback<Void>() {
                            @Override
                            public Void onCall() {
                                return null;
                            }
                        });
                        gameView.flipCardBack(firstCardView, R.drawable.back, new Callback<Void>() {
                            @Override
                            public Void onCall() {
                                changeTurn();
                                return null;
                            }
                        });
                    }
                }, 1500);
            }
            currentPos = -1;
        }
    }

    private void changeTurn() {
        if (isRunning && currentRound == 2) {
            if (currentPlayer == opponents.first) {
                currentPlayer = opponents.second;
            } else {
                currentPlayer = opponents.first;
            }
            currentPlayer.setPlaying(true);
            turn(currentPlayer, 1);
        }
    }

    private void pairMatched(final int idx1, final int idx2) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Card c1 = gameView.getCard(idx1);
                Card c2 = gameView.getCard(idx2);
                gameView.removeCards(idx1, idx2);
                if (currentPlayer.getPlayerType() == PlayerType.CPU) {
                    Cpu cpu = ((Cpu) currentPlayer);
                    cpu.myPairMatched(new Pair<>(new Pair<>(c1.getCardName(), c1.toString()), new Pair<>(c2.getCardName(), c2.toString())));
                    currentPos = -1;
                    turn(opponents.second, 1);
                } else {
                    if (getMyOpponent(currentPlayer).getPlayerType() == PlayerType.CPU)
                        ((Cpu) getMyOpponent(currentPlayer)).pairMatched(new Pair<>(new Pair<>(c1.getCardName(), c1.toString()), new Pair<>(c2.getCardName(), c2.toString())));
                    Guest player = ((Guest) currentPlayer);
                    player.myPairMatched();
                    turn(opponents.first, 1);
                }
                gameView.scoreUpdated(opponents.first.getScore(), opponents.second.getScore());
            }
        }, 1500);
    }

    private void addInCpu(String cardName, String cardId) {
        if (opponents.first.getPlayerType() == PlayerType.CPU) {
            ((Cpu) opponents.first).addInMemory(cardName, cardId);
        }
        if (opponents.second.getPlayerType() == PlayerType.CPU) {
            ((Cpu) opponents.second).addInMemory(cardName, cardId);
        }
    }

    private void matchFinished() {
        isRunning = false;
        gameView.gameFinished(getWinnerName());
    }

    private String getWinnerName() {
        return opponents.first.getScore() > opponents.second.getScore()?opponents.first.getName():opponents.second.getName();
    }

    private void turn(Player player, int round) {
        if (isRunning && gameView.cardCount() >0) {
            gameView.turnChanged(player.getName());
            if (player.getPlayerType() == PlayerType.CPU) {
                gameView.disableClickListeners();
                new Handler().postDelayed(cpuTurn, cpuTurnWaitTime);
            } else {
                gameView.enableClickListeners();
            }
        } else {
            if (isRunning)
                matchFinished();
        }
    }

    private Player getMyOpponent(Player player) {
        if (opponents.first == player)
            return opponents.second;
        else
            return opponents.first;
    }

    @Override
    public void onPlayerMove(View v, int idx) {
        playerMove(v, idx);
    }
}
