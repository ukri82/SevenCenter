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
    private final static int mXMargin = 20;
    float mCardDisplayGap = 15;
    boolean mIsOnRight = false;
    boolean mIsOpen = false;

    BaseView mIndicatorY;
    BaseView mIndicatorG;
    BaseView mIndicatorR;
    BaseView mCurrentIndicator;

    public HandView(DisplayBundle dBundle)
    {
        super("Hand.png", 1024, 1024, dBundle);

        show();

        mIndicatorY = new BaseView("yellowlight.png", 1024, 1024, dBundle);
        mIndicatorG = new BaseView("greenlight.png", 1024, 1024, dBundle);
        mIndicatorR = new BaseView("redlight.png", 1024, 1024, dBundle);
        showIndicator(mIndicatorY);
    }

    public void linkCardViews(Hand hand, ArrayList<CardView> cardViews)
    {
        mHand = hand;
        mCardViews = cardViews;
        for(int i = 0; i < mCardViews.size(); i++)
        {
            if(mIsOpen)
            {
                mCardViews.get(i).open();
            }
            else
            {
                mCardViews.get(i).close();
            }
        }
        placeCards();
    }

    public void reset()
    {
        mHand = null;
        if(mCardViews != null)
        {
            mCardViews.clear();
            mCardViews = null;
        }

        showYellow();
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

        if(mCardViews == null)
        {
            return;
        }

        float cardSizeRatio = 1;
        mCardDisplayGap = 20;
        if(mCardViews.size() > 0)
        {
            if (mIsOpen)
            {
                mCardDisplayGap = (getWidth() - mXMargin * 2) / mCardViews.size();
            } else
            {
                mCardDisplayGap = (getWidth() - mXMargin * 2 - mIndicatorY.getWidth() - mCardViews.get(0).getWidth()) / mCardViews.size();
            }

            cardSizeRatio = getHeight() / 2 / mCardViews.get(0).getHeight();
        }

        for(int i = 0; i < mCardViews.size(); i++)
        {
            mCardViews.get(i).setHeight(mCardViews.get(i).getHeight() * cardSizeRatio);
            mCardViews.get(i).setWidth(mCardViews.get(i).getWidth() * cardSizeRatio);
            float xPos = getX() + mXMargin + mCardDisplayGap * i;
            if(mIsOnRight)
            {
                xPos = getX() + getWidth() - mXMargin - mCardViews.get(0).getWidth() - mCardDisplayGap * (mCardViews.size() - 1 - i);
            }
            float yPos = getY() + getHeight() / 2 - mCardViews.get(i).getHeight() / 2;
            mCardViews.get(i).setPosition(xPos, yPos);
        }
    }


    public void setOpen(boolean isOpen)
    {
        mIsOpen = isOpen;
    }
    private void placeIndicator(BaseView indicator)
    {
        indicator.setWidth(getHeight() / 4);
        indicator.setHeight(getHeight() / 4);

        float xPos = 0;
        float yPos = 0;
        if(mIsOpen)
        {
            xPos = getX() + getWidth() / 2 - indicator.getWidth()/2;
            yPos = getY() - indicator.getHeight();
        }
        else
        {
            xPos = getX() + getWidth() - indicator.getWidth();
            yPos = getY() + getHeight() / 2 - indicator.getHeight() / 2;
            if(mIsOnRight)
            {
                xPos = getX();
            }
        }
        indicator.setPosition(xPos, yPos);
    }

    public float getIndicatorHeight()
    {
        return mIndicatorY.getHeight();
    }
    private void placeIndicators()
    {
        placeIndicator(mIndicatorY);
        placeIndicator(mIndicatorG);
        placeIndicator(mIndicatorR);
    }

    public void prepareInteraction(boolean flag, ICardClickHandler cardClickHandler, ArrayList<CardView> playableCards)
    {
        if(flag)
        {
            for(int i = 0; i < playableCards.size(); i++)
            {
                playableCards.get(i).highlight();
                playableCards.get(i).setClickable(flag, cardClickHandler);
            }
        }
        else
        {
            for(int i = 0; i < mCardViews.size(); i++)
            {
                mCardViews.get(i).removeHighlight();
                mCardViews.get(i).setClickable(flag, cardClickHandler);
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

    public void highlightThru()
    {

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
