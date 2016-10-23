package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Brain;
import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.view.CardView;
import com.candyz.a7center.cards.view.HandView;
import com.candyz.a7center.cards.view.ICardClickHandler;

import java.util.ArrayList;

/**
 * Created by u on 07.10.2016.
 */

public class InteractiveBrain extends Brain implements ICardClickHandler
{
    HandView mHandView;
    Object mSyncToken =  new Object();

    public void linkHandView(HandView handView)
    {
        mHandView = handView;

    }

    CardView mPlayedCardView;

    private ArrayList<CardView> getPlayableCards()
    {
        ArrayList<CardView> allCardViews = mHandView.getCardViews();
        ArrayList<CardView> playableCards = new ArrayList<>();
        for(int i = 0; i < allCardViews.size(); i++)
        {
            if(mTray.isAllowed(allCardViews.get(i).getCard().getSuit(), allCardViews.get(i).getCard().getNumber()))
            {
                playableCards.add(allCardViews.get(i));
            }
        }
        return playableCards;
    }
    @Override
    public Card getNextCard()
    {
        mHandView.prepareInteraction(true, this, getPlayableCards());

        waitForNextPlay();
        Card c = null;
        if(mPlayedCardView != null)
        {
            c = mPlayedCardView.getCard();
            mPlayedCardView = null;
        }
        return c;
    }

    @Override
    public void onClick(CardView cardView)
    {
        if(cardView != null)
        {
            if (mTray.isAllowed(cardView.getCard().getSuit(), cardView.getCard().getNumber()))
            {
                mPlayedCardView = cardView;
                cardPlayFinished();
            }
        }
        else
        {
            ArrayList<Card> possibleCards = getPlayeableCards();
            if(possibleCards.size() != 0)
            {
                return;
            }
            else
            {
                //  No card selected. User clicked pass
                cardPlayFinished();
            }
        }
    }

    void cardPlayFinished()
    {

        mHandView.prepareInteraction(false, null, null);
        synchronized (mSyncToken)
        {
            mSyncToken.notify();
        }
    }

    void waitForNextPlay()
    {
        synchronized (mSyncToken)
        {
            try
            {
                mSyncToken.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
