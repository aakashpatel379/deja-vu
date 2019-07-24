package com.developer.dejavu.gameplay;

import com.developer.dejavu.R;

public final class CardDB {
    private static CardDB instance = new CardDB();
    public static CardDB getInstance(){ return instance; }

    public final Card[] clubs = new Card[]{new Card("A", R.drawable.clubs1, CardColor.CLUBS), new Card("2", R.drawable.clubs2, CardColor.CLUBS), new Card("3", R.drawable.clubs3, CardColor.CLUBS), new Card("4", R.drawable.clubs4, CardColor.CLUBS),
            new Card("5", R.drawable.clubs5, CardColor.CLUBS), new Card("6", R.drawable.clubs6, CardColor.CLUBS), new Card("7", R.drawable.clubs7, CardColor.CLUBS), new Card("8", R.drawable.clubs8, CardColor.CLUBS),
            new Card("9", R.drawable.clubs9, CardColor.CLUBS), new Card("10", R.drawable.clubs10, CardColor.CLUBS), new Card("J", R.drawable.clubs11, CardColor.CLUBS), new Card("Q", R.drawable.clubs12, CardColor.CLUBS), new Card("K", R.drawable.clubs13, CardColor.CLUBS)};
    public final Card[] diamonds = new Card[]{new Card("A", R.drawable.diamonds1, CardColor.DIAMONDS), new Card("2", R.drawable.diamonds2, CardColor.DIAMONDS), new Card("3", R.drawable.diamonds3, CardColor.DIAMONDS), new Card("4", R.drawable.diamonds4, CardColor.DIAMONDS),
            new Card("5", R.drawable.diamonds5, CardColor.DIAMONDS), new Card("6", R.drawable.diamonds6, CardColor.DIAMONDS), new Card("7", R.drawable.diamonds7, CardColor.DIAMONDS), new Card("8", R.drawable.diamonds8, CardColor.DIAMONDS),
            new Card("9", R.drawable.diamonds9, CardColor.DIAMONDS), new Card("10", R.drawable.diamonds10, CardColor.DIAMONDS), new Card("J", R.drawable.diamonds11, CardColor.DIAMONDS), new Card("Q", R.drawable.diamonds12, CardColor.DIAMONDS), new Card("K", R.drawable.diamonds13, CardColor.DIAMONDS)};
}
