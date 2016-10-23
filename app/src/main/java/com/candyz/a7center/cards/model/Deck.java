package com.candyz.a7center.cards.model;

import com.candyz.a7center.cards.Utils;

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

    public void shuffle()
    {
        Utils.shuffle(mCards);
    }
}
