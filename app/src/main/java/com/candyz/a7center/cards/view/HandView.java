package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Hand;

import org.andengine.entity.sprite.ButtonSprite;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class HandView extends BaseView
{
    Hand mHand;
    ArrayList<CardView> mCardViews;
    private final static int mXMargin = 15;
    private final static int mCardDisplayGap = 15;
    boolean mIsOnRight = false;

    BaseView mIndicatorY;
    BaseView mIndicatorG;
    BaseView mIndicatorR;
    BaseView mCurrentIndicator;

    public HandView(Hand hand, ArrayList<CardView> cardViews, DisplayBundle dBundle)
    {
        super("Hand.png", dBundle);
        mHand = hand;
        mCardViews = cardViews;

        show();
        for(int i = 0; i < mCardViews.size(); i++)
        {
           mCardViews.get(i).close();
        }

        mIndicatorY = new BaseView("yellowlight.png", dBundle);
        mIndicatorG = new BaseView("greenlight.png", dBundle);
        mIndicatorR = new BaseView("redlight.png", dBundle);
        showIndicator(mIndicatorY);
    }

    private void showIndicator(BaseView indicator)
    {
        if(indicator == mCurrentIndicator)
            return;

        if(mCurrentIndicator != null)
            mCurrentIndicator.hide();

        mCurrentIndicator = indicator;
        mCurrentIndicator.show();
    }

    public void setOnRight()
    {
        mIsOnRight = true;
    }
    public boolean isOnRight()
    {
        return mIsOnRight;
    }

    private void placeCards()
    {

        for(int i = 0; i < mCardViews.size(); i++)
        {
            float xPos = getX() + mXMargin + mCardDisplayGap * i;
            if(mIsOnRight)
            {
                xPos = getX() + getWidth() - mXMargin - mCardViews.get(0).getWidth() - mCardDisplayGap * (mCardViews.size() - 1 - i);
            }
            float yPos = getY() + getHeight() / 2 - mCardViews.get(i).getHeight() / 2;

            if(mHand.isOpen())
            {
                //xPos = getX() + mXMargin + 2 * mCardDisplayGap * i;
                xPos = getX() + mXMargin + mCardViews.get(i).getWidth() * i;
                mCardViews.get(i).open();
            }
            mCardViews.get(i).setPosition(xPos, yPos);
        }
    }

    private void placeIndicator(BaseView indicator)
    {
        indicator.setWidth(getHeight() / 4);
        indicator.setHeight(getHeight() / 4);

        float xPos = 0;
        float yPos = 0;
        if(mHand.isOpen())
        {
            xPos = getX() + getWidth() / 2 - indicator.getWidth()/2;
            yPos = getY() - indicator.getHeight();
        }
        else
        {
            xPos = getX() + getWidth() - indicator.getWidth();
            yPos = getY() + getHeight() / 2;
            if(mIsOnRight)
            {
                xPos = getX();
            }
        }
        indicator.setPosition(xPos, yPos);
    }
    private void placeIndicators()
    {
        placeIndicator(mIndicatorY);
        placeIndicator(mIndicatorG);
        placeIndicator(mIndicatorR);
    }

    public void prepareInteraction(boolean flag, ICardClickHandler cardClickHandler, ArrayList<CardView> playableCards)
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            mCardViews.get(i).setClickable(flag, cardClickHandler);
        }
        if(flag)
        {
            for(int i = 0; i < playableCards.size(); i++)
            {
                playableCards.get(i).highlight();
            }
        }
        else
        {
            for(int i = 0; i < mCardViews.size(); i++)
            {
                mCardViews.get(i).removeHighlight();
            }
        }
        setIndicatorClickable(flag, cardClickHandler);
    }

    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        placeCards();
        placeIndicators();
    }

    public void showRed()
    {
        showIndicator(mIndicatorR);
    }

    public void showYellow()
    {
        showIndicator(mIndicatorY);
    }
    public void showGreen()
    {
        showIndicator(mIndicatorG);
    }

    private void setIndicatorClickable(boolean flag, final ICardClickHandler cardClickHandler)
    {
        if(flag)
        {
            mDispBundle.mScene.registerTouchArea(mIndicatorG.getSprite());
            mIndicatorG.getSprite().setOnClickListener(new ButtonSprite.OnClickListener()
            {
                @Override
                public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY)
                {
                    cardClickHandler.onClick(null);
                }
            });
        }
        else
        {
            mDispBundle.mScene.unregisterTouchArea(mIndicatorG.getSprite());
            mIndicatorG.getSprite().setOnClickListener(null);
        }
    }

    public CardView getCardView(Card c)
    {
        for(int i = 0; i < mCardViews.size(); i++)
        {
            if(mCardViews.get(i).getCard().getSuit() == c.getSuit() && mCardViews.get(i).getCard().getNumber() == c.getNumber())
            {
                return mCardViews.get(i);
            }
        }
        return null;
    }

    public ArrayList<CardView> getCardViews()
    {
        return mCardViews;
    }
}
