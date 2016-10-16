package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Player;

import org.andengine.entity.text.Text;


/**
 * Created by u on 03.10.2016.
 */

public class PlayerView  extends BaseView
{
    Player mPlayer;
    Text mNameText;
    int mNameGap = 5;
    int mVMargin = 5;

    float mHandPosX;
    float mHandPosY;

    HandView mHandView;



    public PlayerView(Player player, DisplayBundle dispBundle)
    {
        super(player.getImageURL(), dispBundle);

        mPlayer = player;

        show();

        mNameText = new Text(getX(), getY(), mDispBundle.mGeneralTextFont, player.getName(), mDispBundle.mVBOM);
        dispBundle.mScene.attachChild(mNameText);

        mHandPosY = getY() + mVMargin;
    }


    public float getHeight()
    {
        return mSprite.getHeight() * 1.5f + mNameText.getHeight() + mNameGap + mVMargin;
    }

    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        mNameText.setPosition(getX(), getY() - mNameText.getHeight() - mNameGap);
    }

    public float getMiddleY()
    {
        return mSprite.getY() + mSprite.getHeight() / 2;
    }

    public void setHandPosition(float xPos)
    {
        mHandPosX = xPos;
        mHandView.setPosition(mHandPosX, mHandPosY);
    }

    public void assignHand(HandView handView, boolean open)
    {
        mHandView = handView;
        if(open)
        {
            mHandView.mHand.open();
        }
        mHandView.setHeight(getHeight());
        mHandView.setWidth(300);

    }

    public HandView getHand()
    {
        return mHandView;
    }
}
