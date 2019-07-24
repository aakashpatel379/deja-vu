package com.developer.dejavu.model;

import com.developer.dejavu.model.Player;
import com.developer.dejavu.cpu.PlayerType;

public class Guest extends Player {
    public Guest(String name) {
        super(name, PlayerType.GUEST);
    }
    public void myPairMatched() {
        pairMatched++;
        score += 10;
    }
}
