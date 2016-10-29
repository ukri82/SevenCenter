package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Deck;

import java.util.ArrayList;

/**
 * Created by u on 22.10.2016.
 */

public class DeckView extends BaseView
{
    Deck mDeck;

    ArrayList<CardView> mCardViews = new ArrayList<>();

    float mOrigCardWidth;
    float mOrigCardHeight;
    float mX = 0;
    float mY = 0;

    public DeckView(Deck deck, DisplayBundle displayBundle)
    {
        super("", 2,2, displayBundle);

        mDeck = deck;

        for(int j = 0; j < mDeck.get().size(); j++)
        {
            mCardViews.add(new CardView(mDeck.get().get(j), mDispBundle));
            if(j == 0)
            {
                mOrigCardWidth = mCardViews.get(0).getWidth();
                mOrigCardHeight = mCardViews.get(0).getHeight();
            }
        }
    }

    public CardView get(Card c)
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            if(c.isEqual(mCardViews.get(i).getCard()))
            {
                return mCardViews.get(i);
            }
        }
        return null;

    }

    public void setPosition(float x, float y)
    {
        mX = x;
        mY = y;
        placeCards(false);
    }

    private void placeCards(boolean animate)
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            if(animate)
            {
                mCardViews.get(i).animateMove(mX, mY);
            }
            else
            {
                mCardViews.get(i).setPosition(mX, mY);
            }
        }
    }

    public void reset()
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            mCardViews.get(i).reset();
            mCardViews.get(i).close();
        }
        placeCards(false);
    }

    public float getHeight()
    {
        return mOrigCardHeight;
    }
}
