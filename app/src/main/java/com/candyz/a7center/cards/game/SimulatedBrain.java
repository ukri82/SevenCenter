package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Brain;
import com.candyz.a7center.cards.model.Card;

import java.util.ArrayList;

/**
 * Created by u on 07.10.2016.
 */

public class SimulatedBrain extends Brain
{
    @Override
    public Card getNextCard()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        ArrayList<Card> possibleCards = getPlayeableCards();

        Card nextCard = null;
        if(possibleCards.size() != 0)
            nextCard = possibleCards.get(0);
        return nextCard;
    }


    public Card getNextCard1()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        int nextSuit = -1;
        for(int i = 0; i < mSortedHand.size(); i++)
        {
            if(mSortedHand.get(i).size() > 0)
            {
                nextSuit = i;
                if (Math.random() < 0.5)
                    break;
            }
        }
        int nextCard = -1;
        if(nextSuit != -1)
        {
            for(int i = 0; i < mSortedHand.get(nextSuit).size(); i++)
            {
                nextCard = i;
                if(Math.random() < 0.5)
                {
                    break;
                }
            }
        }

        Card c = null;
        if(nextSuit != -1 && nextCard != -1)
        {
            c = mSortedHand.get(nextSuit).get(nextCard);
            mSortedHand.get(nextSuit).remove(nextCard);
        }
        return c;
    }
}
