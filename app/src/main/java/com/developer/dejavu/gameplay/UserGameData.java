package com.developer.dejavu.gameplay;

import android.util.ArraySet;

public class UserGameData {
    private GameData gameData;
    private ArraySet<Card> cards;

    public UserGameData(GameData gameData, ArraySet<Card> cards) {
        this.gameData = gameData;
        this.cards = cards;
    }

    public UserGameData(){}

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public ArraySet<Card> getCards() {
        return cards;
    }

    public void setCards(ArraySet<Card> cards) {
        this.cards = cards;
    }
}
