package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.Utils;
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
    private static float mXMargin = 20;
    float mCardDisplayGap = 15;
    boolean mIsOnRight = false;
    boolean mIsOpen = false;

    BaseView mIndicatorY;
    BaseView mIndicatorG;
    BaseView mIndicatorR;
    BaseView mCurrentIndicator;
    BaseView mPassButton;

    public HandView(DisplayBundle dBundle)
    {
        super("Hand.png", 256, 128, dBundle);

        show();

        mIndicatorY = createIndicator("yellowlight.png");
        mIndicatorG = createIndicator("greenlight.png");
        mIndicatorR = createIndicator("redlight.png");

        mPassButton = new BaseView("pass_button.png", 256, 128, dBundle);
        mPassButton.setHeight(Utils.toPx(mDispBundle.mActivity, 32));
        showIndicator(mIndicatorY);
    }

    private BaseView createIndicator(String imageURL)
    {
        BaseView indicator = new BaseView(imageURL, 64, 64, mDispBundle);
        indicator.setWidth(Utils.toPx(mDispBundle.mActivity, 32));
        indicator.setHeight(Utils.toPx(mDispBundle.mActivity, 32));
        return indicator;
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

    private void calculateCardGap(float cardSizeRatio)
    {
        mCardDisplayGap = 10;
        mXMargin = getWidth() * 0.02f;
        if(mCardViews.size() > 0)
        {
            float totalAvailableWidth = getWidth() - mXMargin * 2;
            if (!mIsOpen)
            {
                totalAvailableWidth -= (mIndicatorY.getWidth() + mCardViews.get(0).getWidth() * cardSizeRatio);
            }
            mCardDisplayGap = totalAvailableWidth / mCardViews.size();
        }
    }
    private void placeCards()
    {

        if(mCardViews == null)
        {
            return;
        }

        float cardSizeRatio = getHeight() * 0.80f / mCardViews.get(0).getHeight();
        calculateCardGap(cardSizeRatio);

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
            //mCardViews.get(i).setPosition(xPos, yPos);
            mCardViews.get(i).animateMove(xPos, yPos);
        }
    }


    public void setOpen(boolean isOpen)
    {
        mIsOpen = isOpen;
    }
    private void placeIndicator(BaseView indicator)
    {
        indicator.setWidth(getHeight() / 4);
        if(mPassButton != indicator)
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
        placeIndicator(mPassButton);
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

    public void highlightThru(ArrayList<Card> playableCards)
    {
        ArrayList<Card> unplayedCards = mHand.getUnplayedCards();
        if(unplayedCards.size() <= 3)
        {
            for (int i = 0; i < playableCards.size(); i++)
            {
                for(int j = 0; j < mCardViews.size(); j++)
                {
                    if (mCardViews.get(j).getCard().isEqual(playableCards.get(i)))
                    {
                        mCardViews.get(j).highlight();
                        break;
                    }
                }
            }
        }
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
        if(mIsOpen)
        {
            showIndicator(mPassButton);
        }
        else
        {
            showIndicator(mIndicatorG);
        }
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
            if(mCardViews.get(i).getCard().isEqual(c))
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
