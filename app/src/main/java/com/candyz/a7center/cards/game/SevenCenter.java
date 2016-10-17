package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Deck;
import com.candyz.a7center.cards.model.Hand;
import com.candyz.a7center.cards.model.IDeckFactory;
import com.candyz.a7center.cards.model.IPlayListener;
import com.candyz.a7center.cards.model.IPlayerFactory;
import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.cards.model.Tray;

import java.util.ArrayList;

/**
 * Created by u on 02.10.2016.
 */

public class SevenCenter implements IPlayListener
{

    ArrayList<Player> mPlayerList;
    IPlayerFactory mPlayerFactory;
    IDeckFactory mDeckFactory;
    Deck mDeck;
    Tray mTray;

    IPlayListener mPlayListener;

    public SevenCenter(IPlayerFactory playerFactory, IDeckFactory deckFactory)
    {
        mPlayerFactory = playerFactory;
        mDeckFactory = deckFactory;
    }

    public void startGame(String interactivePlayerId, int numPlayers)
    {
        mPlayerList = mPlayerFactory.create(interactivePlayerId, numPlayers);
        mDeck = mDeckFactory.create();
        mTray = new SevenCenterTray();

        ArrayList<Hand> hands = createHands(numPlayers);

        for(int i = 0; i < mPlayerList.size(); i++)
        {
            mPlayerList.get(i).assign(hands.get(i));
            mPlayerList.get(i).linkTray(mTray);
            mPlayerList.get(i).registerPlayListener(this);
        }
    }

    public void registerPlayListener(IPlayListener playListener)
    {
        mPlayListener = playListener;
    }

    Object mSyncToken =  new Object();
    void startNewRound()
    {
        PlayThread playThread = new PlayThread();
        playThread.start();
    }

    public Tray getTray()
    {
        return mTray;
    }

    private ArrayList<Hand> createHands(int numPlayers)
    {
        ArrayList<Hand> hands = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++)
        {
            ArrayList<Card> cardList = new ArrayList<>();
            for(int j = i; j < mDeck.get().size(); j += numPlayers)
            {
                Card c = mDeck.get(j);
                c.played(false);
                cardList.add(c);
            }

            hands.add(new Hand(cardList));
        }
        return hands;
    }

    public ArrayList<Player> getPlayerList()
    {
        return mPlayerList;
    }

    @Override
    public void beforePlay(final int playerIndex)
    {
        if(mPlayListener != null)
        {
            mPlayListener.beforePlay(playerIndex);
        }
    }

    @Override
    public void play(int playerIndex, Card c)
    {
        mTray.addCard(c);
        if(mPlayListener != null)
        {
            mPlayListener.play(playerIndex, c);
        }
    }

    public void playCardFinished()
    {
        synchronized(mSyncToken)
        {
            mSyncToken.notify();
        }
    }

    public interface IRoundListener
    {
        public void finished(int playerIndex);
    }

    public void registerRoundListener(IRoundListener roundListener)
    {
        mRoundListener = roundListener;
    }

    IRoundListener mRoundListener;
    public class PlayThread extends Thread
    {
        public void run()
        {
            boolean finished = false;
            while(!finished)
            {

                for(int i = 0; i < mPlayerList.size(); i++)
                {
                    mPlayerList.get(i).play(i);

                    if(mPlayerList.get(i).isFinished())
                    {
                        if(mRoundListener != null)
                        {
                            mRoundListener.finished(i);
                            finished = true;
                            break;
                        }
                    }

                    waitForNextPlay();
                }
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
}
