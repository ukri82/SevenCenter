package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.cards.model.Tray;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class TableView extends BaseView
{
    ArrayList<PlayerView> mPlayerViewList = new ArrayList<>();

    PlayerView mInteractivePlayerView;

    float mInteractivePlayerAreaHeight = 0;

    ArrayList<Player> mPlayerList;
    ArrayList<HandView> mHandViews;

    TrayView mTrayView;
    Tray mTray;

    public TableView(ArrayList<Player> playerList, ArrayList<HandView> handViews, Tray t, float width, float height, DisplayBundle dispBundle)
    {
        super("background.png", dispBundle);

        setWidth(width);
        setHeight(height);
        mInteractivePlayerAreaHeight = height * 0.3f;
        mPlayerList = playerList;
        mHandViews = handViews;
        mTray = t;

        createViews();
        positionPlayerViews();
        positionHandViews();
        positionTrayView();
        positionInteractivePlayer();

    }

    private void positionInteractivePlayer()
    {
        mInteractivePlayerView.getHand().setWidth(mTrayView.getWidth() - mInteractivePlayerView.getWidth());
        mInteractivePlayerView.setPosition(mTrayView.getX(), getHeight() - mInteractivePlayerView.getHeight());
        positionHandView(mInteractivePlayerView);
    }

    private void createViews()
    {
        int interactivePlayerIndex = getInteractivePlayerIndex();
        mInteractivePlayerView = new PlayerView(mPlayerList.get(interactivePlayerIndex), mDispBundle);
        mInteractivePlayerView.assignHand(mHandViews.get(interactivePlayerIndex), true);

        for (int i = interactivePlayerIndex + 1; i < interactivePlayerIndex + mPlayerList.size(); i++)
        {
            int index = i % mPlayerList.size();
            Player p = mPlayerList.get(index);
            PlayerView v = new PlayerView(p, mDispBundle);
            v.assignHand(mHandViews.get(index), false);
            mPlayerViewList.add(v);
        }

        mTrayView = new TrayView(mTray, mDispBundle);
    }

    private void positionPlayerViews()
    {
        for(int i = 0; i < mPlayerViewList.size(); i++)
        {
            float posX = 0.0f;
            int heightOrder = mPlayerViewList.size() - i;
            if(i < mPlayerViewList.size() / 2)
            {
                posX = getWidth() - mPlayerViewList.get(i).getWidth();//  place on right
                mPlayerViewList.get(i).getHand().setOnRight();
                heightOrder = i + 1;
            }

            float posY = getHeight() - mInteractivePlayerAreaHeight - heightOrder * mPlayerViewList.get(i).getHeight();
            mPlayerViewList.get(i).setPosition(posX, posY);
        }
    }

    private void positionHandView(PlayerView playerView)
    {
        int margin = 5;

        float posX = playerView.getX() + playerView.getWidth() + margin;
        if(playerView.getHand().isOnRight())
        {
            posX = playerView.getX() - playerView.getHand().getWidth() - margin;
        }

        playerView.getHand().setPosition(posX, playerView.getMiddleY() - playerView.getHeight() / 2);

    }

    private void positionTrayView()
    {
        float minX = mDispBundle.mCamera.getXMax();
        float maxX = 0;
        float minY = mDispBundle.mCamera.getYMax();
        float maxY = 0;

        for(int i = 0; i < mPlayerViewList.size(); i++)
        {
            HandView handView = mPlayerViewList.get(i).getHand();
            if(mPlayerViewList.get(i).getHand().isOnRight())
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

    public void startPlay(final int playerIndex)
    {
        mHandViews.get(playerIndex).showGreen();
    }

    public void playNextCard(final int playerIndex, final Card c)
    {
        if(c != null)
        {
            CardView cView = mHandViews.get(playerIndex).getCardView(c);
            mTrayView.add(cView);
            mHandViews.get(playerIndex).showYellow();
        }
        else
        {
            mHandViews.get(playerIndex).showRed();
        }
    }
}
