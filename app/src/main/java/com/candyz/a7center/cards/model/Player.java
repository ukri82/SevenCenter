package com.candyz.a7center.cards.model;

import java.util.ArrayList;

/**
 * Created by u on 02.10.2016.
 */

public class Player
{

    String mPhotoURL;
    String mName;
    String mId;
    boolean mIsFemale = true;

    boolean mInteractive = false;
    IPlayListener mPlayListener;

    Brain mBrain;

    Tray mTray;

    public Player(String id, String name, boolean isFemale, String photoURL)
    {
        mPhotoURL = photoURL;
        mName = name;
        mId = id;
        mIsFemale = isFemale;
    }

    public void linkTray(Tray t)
    {
        mTray = t;
        mBrain.linkTray(t);
    }

    public void attachBrain(Brain brain)
    {
        mBrain = brain;
    }
    public Brain getBrain()
    {
        return mBrain;
    }

    public void registerPlayListener(IPlayListener playListener)
    {
        mPlayListener = playListener;
    }

    public String getId()
    {
        return mId;
    }
    public String getName()
    {
        return mName;
    }
    public boolean isFemale()
    {
        return mIsFemale;
    }

    public String getImageURL()
    {
        return mPhotoURL;
    }

    public void setInteractive()
    {
        mInteractive = true;
    }

    public boolean isInteractive()
    {
        return mInteractive;
    }



    public void assign(Hand hand)
    {
        mBrain.memorizeCards(hand);
    }

    public Hand getHand()
    {
        return mBrain.getHand();
    }

    public void play(int playerIndex)
    {
        if (mPlayListener != null)
        {
            mPlayListener.beforePlay(playerIndex);
        }

        Card c = mBrain.getNextCard();

        mBrain.remove(c);

        if (mPlayListener != null)
        {
            mPlayListener.play(playerIndex, c);
        }
    }

    public boolean isFinished()
    {
        return mBrain.isFinished();
    }
}
