package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Hand;
import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.cards.model.Tray;
import com.candyz.a7center.manager.OptionsManager;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class TableView extends BaseView
{
    ArrayList<PlayerView> mPlayerViewList = new ArrayList<>();

    PlayerView mInteractivePlayerView;

    float mTrayViewWidth = 0;
    float mTrayViewHeight = 0;

    ArrayList<Player> mPlayerList;
    ArrayList<HandView> mHandViews;

    TrayView mTrayView;
    Tray mTray;

    public TableView(ArrayList<Player> playerList, ArrayList<HandView> handViews, Tray t, float width, float height, DisplayBundle dispBundle)
    {
        super("background.png", 1024, 1024, dispBundle);

        setWidth(width);
        setHeight(height);
        mTrayViewHeight = height * 0.5f;
        mTrayViewWidth = width * 0.5f;
        mPlayerList = playerList;
        mHandViews = handViews;
        mTray = t;

        createViews();
        positionPlayerViews();
        positionHandViews();
        positionTrayView();
        positionInteractivePlayer();

    }

    public void reset()
    {
        for(int i = 0; i < mHandViews.size(); i++)
        {
            mHandViews.get(i).reset();
        }
    }

    public void initializeHandViews(int playerIndex, Hand hand, ArrayList<CardView> cardViews)
    {
        mHandViews.get(playerIndex).linkCardViews(hand, cardViews);
    }

    private void positionInteractivePlayer()
    {
        if(mPlayerViewList.size() == 0)
            return;

        mInteractivePlayerView.setHeight(mPlayerViewList.get(0).getHeight() / 2);
        mInteractivePlayerView.setWidth(mPlayerViewList.get(0).getWidth() / 2);

        mInteractivePlayerView.setPosition(mTrayView.getX() + mTrayView.getWidth() / 2 - mInteractivePlayerView.getWidth() / 2, getHeight() - mInteractivePlayerView.getHeight());

        float totalInteractivePlayerAreaHeight = getHeight() - mTrayViewHeight - mInteractivePlayerView.getHeight() - mInteractivePlayerView.getHandView().getIndicatorHeight() - Utils.toPx(mDispBundle.mActivity, 5);
        mInteractivePlayerView.setHandViewDimensions(getWidth(), totalInteractivePlayerAreaHeight);
        mInteractivePlayerView.setHandViewPosition(0, getHeight() - totalInteractivePlayerAreaHeight - mInteractivePlayerView.getHeight() - Utils.toPx(mDispBundle.mActivity, 5));
    }

    private void createViews()
    {
        int interactivePlayerIndex = getInteractivePlayerIndex();
        mInteractivePlayerView = new PlayerView(mPlayerList.get(interactivePlayerIndex), mDispBundle);
        mInteractivePlayerView.linkHandView(mHandViews.get(interactivePlayerIndex));
        mPlayerList.get(interactivePlayerIndex).linkUpdateListener(mInteractivePlayerView);

        for (int i = interactivePlayerIndex + 1; i < interactivePlayerIndex + mPlayerList.size(); i++)
        {
            int index = i % mPlayerList.size();
            Player p = mPlayerList.get(index);
            PlayerView v = new PlayerView(p, mDispBundle);
            v.linkHandView(mHandViews.get(index));
            mPlayerViewList.add(v);
        }

        mTrayView = new TrayView(mTray, mDispBundle);
    }

    private void positionPlayerViews()
    {
        if(mPlayerViewList.size() == 0)
            return;

        float playerViewHeight = mTrayViewHeight / ((mPlayerViewList.size() + 1) / 2);
        float playerViewWidth = mPlayerViewList.get(0).getWidth() * playerViewHeight / mPlayerViewList.get(0).getHeight();

        for(int i = 0; i < mPlayerViewList.size(); i++)
        {
            mPlayerViewList.get(i).setHeight(playerViewHeight);
            mPlayerViewList.get(i).setWidth(playerViewWidth);

            float posX = 0.0f;
            int heightOrder = mPlayerViewList.size() - i;
            if(i < mPlayerViewList.size() / 2)
            {
                posX = getWidth() - mPlayerViewList.get(i).getWidth();//  place on right
                mPlayerViewList.get(i).getHandView().setOnRight();
                heightOrder = i + 1;
            }

            float posY = mTrayViewHeight - heightOrder * mPlayerViewList.get(i).getHeight() + 10;
            mPlayerViewList.get(i).setPosition(posX, posY);
        }
    }

    private void positionHandView(PlayerView playerView)
    {
        int margin = 5;
        float handViewWidth = (getWidth() - mTrayViewWidth) / 2 - playerView.getWidth();
        float handViewHeight = playerView.getHeight();
        playerView.setHandViewDimensions(handViewWidth, handViewHeight);

        float posX = playerView.getX() + playerView.getWidth() + margin;
        if(playerView.getHandView().isOnRight())
        {
            posX = playerView.getX() - playerView.getHandView().getWidth() - margin;
        }

        playerView.setHandViewPosition(posX, playerView.getY());

    }

    private void positionTrayView()
    {
        float minX = mDispBundle.mCamera.getXMax();
        float maxX = 0;
        float minY = mDispBundle.mCamera.getYMax();
        float maxY = 0;

        for(int i = 0; i < mPlayerViewList.size(); i++)
        {
            HandView handView = mPlayerViewList.get(i).getHandView();
            if(mPlayerViewList.get(i).getHandView().isOnRight())
            {
                if(handView.getX() > maxX)
                {
                    maxX = handView.getX();
                }

            }
            else
            {
                if(handView.getX() + handView.getWidth() < minX)
                {
                    minX = handView.getX() + handView.getWidth();
                }
            }

            if(mPlayerViewList.get(i).getY() + mPlayerViewList.get(i).getHeight() > maxY)
            {
                maxY = mPlayerViewList.get(i).getY() + mPlayerViewList.get(i).getHeight();
            }

            if(handView.getY() < minY)
            {
                minY = handView.getY();
            }
        }

        mTrayView.setPosition(minX, minY);
        mTrayView.setWidth(maxX - minX);
        mTrayView.setHeight(maxY - minY);
    }

    private void positionHandViews()
    {
        for(int i = 0; i < mPlayerViewList.size(); i++)
        {
            positionHandView(mPlayerViewList.get(i));
        }

    }

    private int getInteractivePlayerIndex()
    {
        int interactivePlayerIndex = -1;
        for (int i = 0; i < mPlayerList.size(); i++)
        {
            if(mPlayerList.get(i).isInteractive())
            {
                interactivePlayerIndex = i;
            }
        }
        return interactivePlayerIndex;
    }

    public void beforePlay(final int playerIndex)
    {
        mHandViews.get(playerIndex).showGreen();
    }


    public void play(final int playerIndex, final Card c)
    {
        if(c != null)
        {
            CardView cView = mHandViews.get(playerIndex).getCardView(c);
            mTrayView.add(cView);
            for(int i = 0; i < mPlayerViewList.size(); i++)
            {
                if(mPlayerViewList.get(i).getHandView() == mHandViews.get(playerIndex))
                {
                    mPlayerViewList.get(i).highlightThru();
                    break;
                }
            }

            mHandViews.get(playerIndex).showYellow();
        }
        else
        {
            mHandViews.get(playerIndex).showRed();
        }
    }
}
