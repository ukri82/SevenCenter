package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.Player;

import org.andengine.entity.text.Text;


/**
 * Created by u on 03.10.2016.
 */

public class PlayerView  extends BaseView implements Player.IPlayerListener
{
    Player mPlayer;
    Text mNameText;
    BaseView mBrilliancyView;
    int mNameGap = 5;
    int mVMargin = 5;


    HandView mHandView;

    public PlayerView(Player player, DisplayBundle dispBundle)
    {
        super(player.getImageURL(), 128, 128, dispBundle);

        mPlayer = player;

        mNameGap = (int)Utils.toPx(dispBundle.mActivity, 5);
        mVMargin = (int)Utils.toPx(dispBundle.mActivity, 5);

        show();

        mNameText = new Text(getX(), getY(), mDispBundle.mGeneralTextFont, player.getName(), mDispBundle.mVBOM);
        dispBundle.mScene.attachChild(mNameText);

        if(!mPlayer.isInteractive())
            createBrilliancyView();
    }

    void createBrilliancyView()
    {
        mBrilliancyView = new BaseView("bulb" + mPlayer.getBrilliancy() + ".png", 128, 64, mDispBundle);
        float height = super.getHeight() * 0.2f;
        float ratio = height / mBrilliancyView.getHeight() * mPlayer.getBrilliancy();
        mBrilliancyView.setWidth(mBrilliancyView.getWidth() * ratio);
        mBrilliancyView.setHeight(height);
        mBrilliancyView.show();
    }

    public float getHeight()
    {
        float height = mSprite.getHeight() + mNameText.getHeight() + mNameGap + mVMargin;
        if(mBrilliancyView != null)
            height = height + mBrilliancyView.getHeight() + mNameGap;

        return height;
    }

    public void setPosition(float x, float y)
    {
        mNameText.setPosition(x, y);
        float currPos = y + mNameText.getHeight() + mNameGap;

        if(mBrilliancyView != null)
        {
            mBrilliancyView.setPosition(x, currPos);
            currPos += mBrilliancyView.getHeight() + mNameGap;
        }
        mSprite.setPosition(x, currPos);
    }

    public void setHeight(float height)
    {
        float ratio = height / getHeight();
        mSprite.setHeight(mSprite.getHeight() * ratio);
        mNameText.setHeight(mNameText.getHeight() * ratio);
        if(mBrilliancyView != null)
            mBrilliancyView.setHeight(mBrilliancyView.getHeight() * ratio);
    }

    public float getY()
    {
        float y = super.getY();
        if(mNameText != null)
            y = mNameText.getY();
        return y;
    }

    public void setHandViewPosition(float xPos, float yPos)
    {
        mHandView.setPosition(xPos, yPos);
    }

    public void linkHandView(HandView handView)
    {
        mHandView = handView;
    }

    public void setHandViewDimensions(float width, float height)
    {
        mHandView.setHeight(height);
        mHandView.setWidth(width);

    }

    public HandView getHandView()
    {
        return mHandView;
    }

    public void highlightThru()
    {
        if(!mPlayer.isInteractive())
        {
            mHandView.highlightThru(mPlayer.getBrain().getPlayeableCards());
        }
    }

    @Override
    public void onUpdate()
    {
        mNameText.setText(mPlayer.getName());
        mImageURL = mPlayer.getImageURL();
        float x = mSprite.getX();
        float y = getY();
        float width = mSprite.getWidth();
        float height = getHeight();
        float spriteHeight = mSprite.getHeight();

        updateSprite();

        mSprite.setHeight(spriteHeight);
        setPosition(x, y);
        setWidth(width);
        setHeight(height);
    }
}
