package com.developer.dejavu.gameplay;

import android.support.annotation.DrawableRes;
import android.util.Pair;
import android.view.View;

import com.developer.dejavu.Callback;
import com.developer.dejavu.cpu.Player;

public interface GameView {
    public Card getCard(int position);
    int getCardIndex(Card card);
    void disableClickListeners();
    void enableClickListeners();

    void flipCard(View view, @DrawableRes int image);
    void flipCardBack(View view, @DrawableRes int image, Callback<Void> callback);
    int cardCount();
    View getCardView(int position);
    void removeCards(int idx1, int idx2);
    void pairMatched(Pair<Card, Card> cardPair);
    void scoreUpdated(int p1, int p2);
    void turnChanged(String playerName);
    void gameFinished(String winner);
    void onPlayerMove(PlayerMoveCallback playerMoveCallback);
    void disableCard(int position);
    void enableCard(int position);
}
