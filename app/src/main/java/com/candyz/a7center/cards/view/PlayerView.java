package com.candyz.a7center.cards.view;

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
        super(player.getImageURL(), 1024, 1024, dispBundle);

        mPlayer = player;

        show();

        mNameText = new Text(getX(), getY(), mDispBundle.mGeneralTextFont, player.getName(), mDispBundle.mVBOM);
        dispBundle.mScene.attachChild(mNameText);

        createBrilliancyView();
    }

    void createBrilliancyView()
    {
        mBrilliancyView = new BaseView("bulb" + mPlayer.getBrilliancy() + ".png", 1024, 1024, mDispBundle);
        float height = super.getHeight() * 0.2f;
        float ratio = height / mBrilliancyView.getHeight() * mPlayer.getBrilliancy();
        mBrilliancyView.setWidth(mBrilliancyView.getWidth() * ratio);
        mBrilliancyView.setHeight(height);
        mBrilliancyView.show();
    }

    public float getHeight()
    {
        return mSprite.getHeight() + mNameText.getHeight() + mBrilliancyView.getHeight() + mNameGap + mVMargin;
    }

    public void setPosition(float x, float y)
    {
        mNameText.setPosition(x, y);
        mBrilliancyView.setPosition(x, y + mNameText.getHeight());
        mSprite.setPosition(x, mBrilliancyView.getY() + mBrilliancyView.getHeight() + mNameGap);
    }

    public void setHeight(float height)
    {
        float ratio = height / getHeight();
        mSprite.setHeight(mSprite.getHeight() * ratio);
        mNameText.setHeight(mNameText.getHeight() * ratio);
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

        updateSprite();

        setPosition(x, y);
        setWidth(width);
        setHeight(height);
    }
}
