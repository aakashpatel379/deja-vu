package com.developer.dejavu.model;

import com.developer.dejavu.cpu.PlayerType;

public class Player {
    protected int score;
    private String name;
    private PlayerType playerType;
    protected int cardFlipped;
    protected int pairMatched;
    private boolean playing;

    public Player() { }
    public Player(String name, PlayerType playerType) {
        this.name = name;
        this.playerType = playerType;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCardFlipped() {
        return cardFlipped;
    }

    public int getPairMatched() {
        return pairMatched;
    }
}
