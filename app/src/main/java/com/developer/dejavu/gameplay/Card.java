package com.developer.dejavu.gameplay;

import android.util.Log;

import androidx.annotation.DrawableRes;

public class Card {
    private String cardName;
    private @DrawableRes
    int cardImage;
    private CardColor cardColor;

    public Card(String cardName, @DrawableRes int cardImage, CardColor cardColor) {
        this.cardName = cardName;
        this.cardImage = cardImage;
        this.cardColor = cardColor;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    @Override
    public String toString() {
        return cardColor.name()+"_"+cardName;
    }

    @Override
    public boolean equals(Object obj) {
        Log.i("Card", "Comparing: "+obj.toString()+", "+toString());
        return obj.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        return (cardColor.name().hashCode()*cardName.hashCode());
    }

    public static Card parse(String cardKey) {
        if (cardKey.contains("_")) {
            String[] attrs = cardKey.split("_");
            return new Card(attrs[1], 0, CardColor.valueOf(attrs[0]));
        }
        return null;
    }
}
