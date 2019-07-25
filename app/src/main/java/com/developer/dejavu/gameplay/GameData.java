package com.developer.dejavu.gameplay;

import com.developer.dejavu.model.Player;

/**
 * Gamedata model for storing and maintaining player information.
 */
public class GameData {
    private Player p1;
    private Player p2;
    private int currentPosition;

    public GameData(Player p1, Player p2, int currentPosition) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPosition = currentPosition;
    }

    public GameData() {   }
    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
