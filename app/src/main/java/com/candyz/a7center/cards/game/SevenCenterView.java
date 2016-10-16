package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Hand;
import com.candyz.a7center.cards.model.IPlayListener;
import com.candyz.a7center.cards.view.BaseView;
import com.candyz.a7center.cards.view.CardView;
import com.candyz.a7center.cards.view.DisplayBundle;
import com.candyz.a7center.cards.view.HandView;
import com.candyz.a7center.cards.view.TableView;

import org.andengine.entity.sprite.ButtonSprite;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class SevenCenterView extends BaseView implements IPlayListener
{
    SevenCenter mSevenCenter;

    TableView mTableView;

    final float mTableViewWidthPercent = 0.8f;



    public SevenCenterView(SevenCenter sevenCenter, DisplayBundle dispBundle)
    {
        super("background.png", dispBundle);

        mSevenCenter = sevenCenter;
        ArrayList<HandView> handViews = createHandViews();
        mTableView = new TableView(sevenCenter.getPlayerList(), handViews, sevenCenter.getTray(), mDispBundle.mCamera.getWidth() * mTableViewWidthPercent, mDispBundle.mCamera.getHeight(), dispBundle);

        mSevenCenter.registerPlayListener(this);
        loadPlayButtonGraphics();
    }

    BaseView mPlayButton;
    private void loadPlayButtonGraphics()
    {
        mPlayButton = new BaseView("play_button.png", mDispBundle);
        mPlayButton.setPosition(mTableView.getX() + mTableView.getWidth(), 10);
        mPlayButton.setHeight(60);
        mPlayButton.setWidth(mDispBundle.mCamera.getWidth() * (1 - mTableViewWidthPercent));
        mPlayButton.show();

        mDispBundle.mScene.registerTouchArea(mPlayButton.getSprite());
        mPlayButton.getSprite().setOnClickListener(new ButtonSprite.OnClickListener()
        {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY)
            {
                mSevenCenter.startNewRound();
            }
        });
    }

    private ArrayList<HandView> createHandViews()
    {
        ArrayList<HandView> handViews = new ArrayList<>();
        for(int i = 0; i < mSevenCenter.getPlayerList().size(); i++)
        {
            Hand hand = mSevenCenter.getPlayerList().get(i).getHand();
            ArrayList<CardView> cardViews = new ArrayList<>();
            for(int j = 0; j < hand.get().size(); j++)
            {
                cardViews.add(new CardView(hand.get().get(j), mDispBundle));
            }
            HandView handView = new HandView(hand, cardViews, mDispBundle);
            handViews.add(handView);

            if(mSevenCenter.getPlayerList().get(i).isInteractive())
            {
                ((InteractiveBrain)mSevenCenter.getPlayerList().get(i).getBrain()).linkHandView(handView);
            }
        }
        return handViews;
    }

    @Override
    public void beforePlay(final int playerIndex)
    {
        mDispBundle.mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mTableView.startPlay(playerIndex);
            }
        });
    }

    @Override
    public void play(final int playerIndex, final Card c)
    {
        mDispBundle.mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mTableView.playNextCard(playerIndex, c);
                mSevenCenter.playCardFinished();
            }
        });

    }
}
