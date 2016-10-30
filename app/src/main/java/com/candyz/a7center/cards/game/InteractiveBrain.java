package com.candyz.a7center.cards.game;

import android.content.Context;
import android.os.Vibrator;

import com.candyz.a7center.cards.model.Brain;
import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.view.CardView;
import com.candyz.a7center.cards.view.ChatView;
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

    ChatView mChatView;
    public void linkChatView(ChatView chatView)
    {
        mChatView = chatView;
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
        ArrayList<CardView> playableCards = getPlayableCards();
        if(playableCards.size() == 0)
        {
            mChatView.addStatus("Click green button to pass, if you want", false);
        }

        mHandView.prepareInteraction(true, this, playableCards);

        waitForNextPlay();

        Card c = null;
        if(mPlayedCardView != null)
        {
            c = mPlayedCardView.getCard();
            mPlayedCardView = null;
            Vibrator v = (Vibrator) mHandView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(30);
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
                if(mChatView != null)
                {
                    mChatView.addStatus("Please play one of the highlighted cards.", true);
                }
                return;
            }
            else
            {
                //  No card selected. User clicked pass
                mChatView.addStatus("", false);
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
