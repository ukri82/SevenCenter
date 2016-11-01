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
    int mBrilliancy = 1;

    boolean mInteractive = false;
    IPlayListener mPlayListener;

    Brain mBrain;

    Tray mTray;

    public interface IPlayerListener
    {
        public void onUpdate();
    }

    IPlayerListener mUpdateListener;

    public Player(String id, String name, boolean isFemale, String photoURL, int brilliancy)
    {
        mPhotoURL = photoURL;
        mName = name;
        mId = id;
        mIsFemale = isFemale;
        mBrilliancy = brilliancy;
    }

    public void linkUpdateListener(IPlayerListener listener)
    {
        mUpdateListener = listener;
    }
    public void linkTray(Tray t)
    {
        mTray = t;
        mBrain.linkTray(t);
    }

    public void attachBrain(Brain brain)
    {
        mBrain = brain;
        mBrain.setBrilliancy(mBrilliancy);
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
    public void update(String name, String url)
    {
        mName = name;
        mPhotoURL = url;
        if(mUpdateListener != null)
            mUpdateListener.onUpdate();
    }

    public boolean isFemale()
    {
        return mIsFemale;
    }

    public int getBrilliancy()
    {
        return mBrilliancy;
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
