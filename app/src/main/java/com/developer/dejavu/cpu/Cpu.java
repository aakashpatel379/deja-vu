package com.developer.dejavu.cpu;

import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;

import com.developer.dejavu.model.Player;

import java.util.HashMap;
import java.util.Random;

public class Cpu extends Player {
    private HashMap<String, ArraySet<String>> cardsInMemory = new HashMap<>();
    private int cardCount = 0;
    private Random random = new Random();

    public Cpu() {
        super("CPU", PlayerType.CPU);
    }

    public void addInMemory(String value, String cardKey) {
        if (cardsInMemory.containsKey(value)) {
            cardsInMemory.get(value).add(cardKey);
        } else {
            ArraySet<String> indexes = new ArraySet<>();
            indexes.add(cardKey);
            cardsInMemory.put(value, indexes);
        }
    }

    public boolean hasValue(String value) {
        return cardsInMemory.containsKey(value);
    }

    public String getCardKey(String value, int idx) {
        return cardsInMemory.get(value).valueAt(idx);
    }

    public void myPairMatched(Pair<Pair<String, String>, Pair<String, String>> pair) {
        pairMatched++;
        score += 10;
        pairMatched(pair);
    }

    public void pairMatched(Pair<Pair<String, String>, Pair<String, String>> pair) {
        removeFromMemory(pair.first.first, pair.first.second);
        removeFromMemory(pair.second.first, pair.second.second);
        //TODO:
    }

    private void removeFromMemory(String value, String cardKey) {
        Log.d("Cpu", "removing card from cpu memory: "+value);
        if (cardsInMemory.containsKey(value)) {
            if (cardsInMemory.get(value).contains(cardKey)) {
                if (cardsInMemory.get(value).remove(cardKey))
                    Log.d("Cpu", "Card removed "+value+" : "+cardKey);
                else {
                    Log.e("Cpu", "Card not removed "+value+" : "+cardKey);
                }
            } else {
                Log.e("Cpu", value+" Doesn't contains "+cardKey);
            }
        } else {
            Log.e("Cpu", "Cpu Doesn't contains "+value);
        }
    }

    public int turn(int previousIndex, int round, int currentCardCount) {
        int idx = random.nextInt(currentCardCount);
        while (previousIndex == idx) {
            idx = random.nextInt(currentCardCount);
        }
        return idx;
    }

//    public String getPair()

    public String hasPair() {
        int x = 0;
        for (ArraySet<String> value : cardsInMemory.values()) {
            if (value.size() > 1)
                return getCardKey(x);
            x++;
        }
        return null;
    }

    String getCardKey(int index) {
        return (String) cardsInMemory.keySet().toArray()[index];
    }
}
