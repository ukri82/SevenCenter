package com.candyz.a7center.cards.model;

import java.util.ArrayList;

/**
 * Created by u on 02.10.2016.
 */

public class Hand
{
    ArrayList<Card> mCards = new ArrayList<>();
    boolean mIsOpen = false;

    public Hand(ArrayList<Card> cards)
    {
        mCards =  cards;
    }

    public void add(Card c)
    {
        mCards.add(c);
    }

    public ArrayList<Card> get()
    {
        return mCards;
    }

    public void open()
    {
        mIsOpen = true;
    }

    public boolean isOpen()
    {
        return mIsOpen;
    }
}
