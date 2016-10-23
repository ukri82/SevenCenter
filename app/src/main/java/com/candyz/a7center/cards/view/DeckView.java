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
            if(c.getSuit() == mCardViews.get(i).getCard().getSuit() && c.getNumber() == mCardViews.get(i).getCard().getNumber())
            {
                return mCardViews.get(i);
            }
        }
        return null;

    }

    public void reset()
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            mCardViews.get(i).reset();
        }
    }
}
