package com.developer.dejavu.cpu;

public class CardRange {
    private int from, to;
    public CardRange(int from, int to) {
        this.from = from;
        this.to = to;
    }


    public int getMinRange() {
        return from;
    }

    public int getMaxRange() {
        return to;
    }
}
