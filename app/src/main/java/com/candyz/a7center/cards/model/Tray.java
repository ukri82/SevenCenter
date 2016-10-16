package com.candyz.a7center.cards.model;

import java.util.ArrayList;

/**
 * Created by u on 04.10.2016.
 */

public class Tray
{
    protected ArrayList<ArrayList<Integer>> mSortedHand;

    public Tray()
    {
        reset();
    }

    public void addCard(Card c)
    {
        if(c != null)
        {
            mSortedHand.get(c.getSuit() - 1).set(c.getNumber() - 2, 1);
        }
    }

    public void reset()
    {
        mSortedHand = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < 4; i++)
        {
            mSortedHand.add(new ArrayList<Integer>());
            for(int j = 0; j < 14; j++)
            {
                mSortedHand.get(i).add(0);
            }
        }
    }

    public ArrayList<ArrayList<Integer>> getCards()
    {
        return mSortedHand;
    }



    public boolean isAllowed(int suit, int number)
    {
        return false;
    }
}
