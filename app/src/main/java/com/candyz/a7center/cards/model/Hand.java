package com.candyz.a7center.cards.model;

import java.util.ArrayList;

/**
 * Created by u on 02.10.2016.
 */

public class Hand
{
    ArrayList<Card> mCards = new ArrayList<>();

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

    public ArrayList<Card> getUnplayedCards()
    {
        ArrayList<Card> unplayedCards = new ArrayList<>();
        for(int i = 0; i < mCards.size(); i++)
        {
            if(!mCards.get(i).isPlayed())
            {
                unplayedCards.add(mCards.get(i));
            }
        }
        return unplayedCards;
    }

}
