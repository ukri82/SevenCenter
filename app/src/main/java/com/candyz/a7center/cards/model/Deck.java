package com.candyz.a7center.cards.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by u on 02.10.2016.
 */

public class Deck
{
    ArrayList<Card> mCards;

    public Deck()
    {

    }

    public void set(ArrayList<Card> cards)
    {
        mCards = cards;
    }
    public Card get(int index)
    {
        return mCards.get(index);
    }

    public ArrayList<Card> get()
    {
        return mCards;
    }

    public static int getSpadeSuit()
    {
        return 2;
    }

    /*public void shuffle()
    {
        Random ran = new Random();
        for(int i = mCards.size() - 1; i > 0; i--)
        {
            int nextIndex = ran.nextInt(i);
            Card temp = mCards.get(i);
            mCards.set(i, mCards.get(nextIndex));
            mCards.set(nextIndex, temp);
        }
    }*/
}
