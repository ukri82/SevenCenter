package com.candyz.a7center.cards.model;

/**
 * Created by u on 02.10.2016.
 */

public class Data
{
    Deck mDeck;

    private static final Data INSTANCE = new Data();

    public Data()
    {
        mDeck = new Deck();
    }

    public Deck getDeck()
    {
        return mDeck;
    }

    public static Data getInstance()
    {
        return INSTANCE;
    }
}
