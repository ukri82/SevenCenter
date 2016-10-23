package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Hand;
import com.candyz.a7center.cards.model.IPlayListener;
import com.candyz.a7center.cards.view.BaseView;
import com.candyz.a7center.cards.view.CardView;
import com.candyz.a7center.cards.view.ChatView;
import com.candyz.a7center.cards.view.DeckView;
import com.candyz.a7center.cards.view.DisplayBundle;
import com.candyz.a7center.cards.view.HandView;
import com.candyz.a7center.cards.view.ScoreCardView;
import com.candyz.a7center.cards.view.TableView;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class SevenCenterView extends BaseView implements IPlayListener, SevenCenter.IRoundListener
{
    SevenCenter mSevenCenter;

    TableView mTableView;

    final float mTableViewWidthPercent = 0.8f;
    final float mTableViewHeightPercent = 0.95f;

    ChatView mChatView;
    ScoreCardView mScoreCardView;

    DeckView mDeckView;


    public SevenCenterView(SevenCenter sevenCenter, DisplayBundle dispBundle)
    {
        super("background.png", 1024, 1024, dispBundle);

        mSevenCenter = sevenCenter;
        mSevenCenter.registerRoundListener(this);
        ArrayList<HandView> handViews = createHandViews();
        mTableView = new TableView(sevenCenter.getPlayerList(), handViews, sevenCenter.getTray(),
                mDispBundle.mCamera.getWidth() * mTableViewWidthPercent, mDispBundle.mCamera.getHeight() * mTableViewHeightPercent, dispBundle);

        mSevenCenter.registerPlayListener(this);
        loadPlayButtonGraphics();
        createChatView();
        createScoreCardView();

        mDeckView = new DeckView(mSevenCenter.getDeck(), mDispBundle);

    }

    private void reset()
    {
        mTableView.reset();
        mDeckView.reset();
    }


    private void createScoreCardView()
    {
        mScoreCardView = new ScoreCardView(mDispBundle);
        mScoreCardView.setPosition(mTableView.getX() + mTableView.getWidth(), mPlayButton.getY() + mPlayButton.getHeight() + 5);
        mScoreCardView.setWidth(mDispBundle.mCamera.getWidth() - mTableView.getWidth());
        mScoreCardView.setHeight(mDispBundle.mCamera.getHeight() - mScoreCardView.getY());
        mScoreCardView.setPlayers(mSevenCenter.getPlayerList());
        mScoreCardView.show();
    }

    private void createChatView()
    {
        mChatView = new ChatView(mDispBundle);
        mChatView.setPosition(0, mTableView.getHeight());
        mChatView.setHeight(mDispBundle.mCamera.getHeight() - mTableView.getHeight());
        mChatView.setWidth(mTableView.getWidth());
        mChatView.show();
    }

    BaseView mPlayButton;
    private void loadPlayButtonGraphics()
    {
        mPlayButton = new BaseView("play_button.png", 1024, 1024, mDispBundle);
        mPlayButton.setPosition(mTableView.getX() + mTableView.getWidth(), 10);
        mPlayButton.setHeight(100);
        mPlayButton.setWidth(mDispBundle.mCamera.getWidth() * (1 - mTableViewWidthPercent));
        mPlayButton.show();

        mDispBundle.mScene.registerTouchArea(mPlayButton.getSprite());
        mPlayButton.getSprite().setOnClickListener(new ButtonSprite.OnClickListener()
        {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY)
            {
                reset();
                mSevenCenter.startNewRound();
                initializeHandViews();
                mSevenCenter.triggerGame();
            }
        });
    }

    private ArrayList<HandView> createHandViews()
    {
        ArrayList<HandView> handViews = new ArrayList<>();
        for(int i = 0; i < mSevenCenter.getPlayerList().size(); i++)
        {
            HandView handView = new HandView(mDispBundle);
            handViews.add(handView);

            if(mSevenCenter.getPlayerList().get(i).isInteractive())
            {
                ((InteractiveBrain)mSevenCenter.getPlayerList().get(i).getBrain()).linkHandView(handView);
                handView.setOpen(true);
            }
        }
        return handViews;
    }

    private void initializeHandViews()
    {
        for(int i = 0; i < mSevenCenter.getPlayerList().size(); i++)
        {
            Hand hand = mSevenCenter.getPlayerList().get(i).getHand();
            ArrayList<CardView> cardViews = new ArrayList<>();
            for(int j = 0; j < hand.get().size(); j++)
            {
                //cardViews.add(new CardView(hand.get().get(j), mDispBundle));
                cardViews.add(mDeckView.get(hand.get().get(j)));
            }

            mTableView.initializeHandViews(i, hand, cardViews);
        }
    }

    @Override
    public void beforePlay(final int playerIndex)
    {
        mDispBundle.mActivity.runOnUpdateThread(new Runnable()
        {
            @Override
            public void run()
            {
                mTableView.beforePlay(playerIndex);
            }
        });
    }

    @Override
    public void play(final int playerIndex, final Card c)
    {
        mDispBundle.mActivity.runOnUpdateThread(new Runnable()
        {
            @Override
            public void run()
            {
                mTableView.play(playerIndex, c);
                mSevenCenter.playCardFinished();
            }
        });

    }

    @Override
    public void finished(int playerIndex)
    {
        mChatView.addStatus("Round finished");
        mScoreCardView.updateScore(mSevenCenter.getScoreCard());
    }
}
