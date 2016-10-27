package com.candyz.a7center.cards.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by u on 07.10.2016.
 */

abstract public class Brain
{
    protected ArrayList<ArrayList<Card>> mSortedHand;
    protected Tray mTray;
    protected Hand mHand;
    protected int mBrilliancy = 1;

    public Hand getHand()
    {
        return mHand;
    }
    public void memorizeCards(Hand hand)
    {
        mSortedHand = new ArrayList<ArrayList<Card>>();
        for(int i = 0; i < 4; i++)
        {
            mSortedHand.add(new ArrayList<Card>());
        }
        for(int j = 0; j < hand.get().size(); j++)
        {
            Card c = hand.get().get(j);
            mSortedHand.get(c.mSuit - 1).add(c);
        }

        mHand = new Hand(new ArrayList<Card>());
        for(int i = 0; i < 4; i++)
        {
            Collections.sort(mSortedHand.get(i), new Brain.CardComparator());
            mHand.get().addAll(mSortedHand.get(i));
        }
    }

    public void setBrilliancy(int brilliancy)
    {
        mBrilliancy = brilliancy;
    }

    public class CardComparator implements Comparator<Card>
    {
        @Override
        public int compare(Card o1, Card o2)
        {
            return o1.getNumber() - o2.getNumber();
        }
    }

    public void linkTray(Tray tray)
    {
        mTray = tray;
    }

    protected ArrayList<Card> getPlayeableCards()
    {
        ArrayList<Card> possibleCards = new ArrayList<>();
        for(int i = 0; i < mSortedHand.size(); i++)
        {
            for(int j = 0; j < mSortedHand.get(i).size(); j++)
            {
                Card currentCard = mSortedHand.get(i).get(j);
                if(!currentCard.isPlayed() && mTray.isAllowed(currentCard.getSuit(), currentCard.getNumber()))
                {
                    possibleCards.add(currentCard);
                }
            }
        }
        return possibleCards;
    }

    abstract public Card getNextCard();

    public void remove(Card c)
    {
        if(c != null)
        {
            mSortedHand.get(c.getSuit() - 1).remove(c);
        }

    }

    public boolean isFinished()
    {
        boolean finished = true;
        for(int i = 0; i < mSortedHand.size(); i++)
        {
            if(mSortedHand.get(i).size() > 0)
            {
                finished = false;
                break;
            }
        }
        return finished;
    }

    public int getPenalty()
    {
        int penalty = 0;
        for(int i = 0; i < mSortedHand.size(); i++)
        {
            for(int j = 0; j < mSortedHand.get(i).size(); j++)
            {
                penalty += mSortedHand.get(i).get(j).getNumber();
            }
        }
        return penalty;
    }
}
