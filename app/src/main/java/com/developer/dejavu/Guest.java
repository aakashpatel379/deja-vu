package com.developer.dejavu;

import android.util.Pair;

import com.developer.dejavu.cpu.Player;
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
