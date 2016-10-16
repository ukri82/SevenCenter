package com.candyz.a7center.cards.model;

/**
 * Created by u on 02.10.2016.
 */

public class Card
{
    int mSuit;
    int mNumber;
    String mImageURL;
    String mBackImageURL;
    boolean mIsPlayed;

    public Card(int suit, int number)
    {
        mSuit = suit;
        mNumber = number;
        int anIndex = (14 - number) * 4 + suit;
        mImageURL = "classic-cards/" + anIndex + ".png";
        mBackImageURL = "classic-cards/b2fv.png";
        mIsPlayed = false;
    }

    public int getSuit()
    {
        return mSuit;
    }
    public int getNumber()
    {
        return mNumber;
    }

    public String getImageURL()
    {
        return mImageURL;
    }

    public String getBackImageURL()
    {
        return mBackImageURL;
    }

    public void played(boolean flag)
    {
        mIsPlayed = flag;
    }

    public boolean isPlayed()
    {
        return mIsPlayed;
    }
}
