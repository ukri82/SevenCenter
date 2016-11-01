package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.Player;

import org.andengine.entity.text.Text;

import java.util.ArrayList;

/**
 * Created by u on 18.10.2016.
 */

public class ScoreCardView extends BaseView
{
    ArrayList<Player> mPlayerList;
    ArrayList<Text> mPlayerNames;
    ArrayList<Text> mScoreInfo;
    float mPlayerInfoHeight = 50f;

    public ScoreCardView(DisplayBundle displayBundle)
    {
        super("chat_bg.png", 1024, 1024, displayBundle);

        mPlayerInfoHeight = Utils.toPx(mDispBundle.mActivity, 15);
    }

    public void setPlayers(ArrayList<Player> players)
    {
        mPlayerList = players;
        mPlayerNames = new ArrayList<>();
        mScoreInfo = new ArrayList<>();
        for(int i = 0; i < mPlayerList.size(); i++)
        {
            Text nameText = new Text(2, i * mPlayerInfoHeight, mDispBundle.mGeneralTextFont, mPlayerList.get(i).getName(), mDispBundle.mVBOM);
            nameText.setWidth(getWidth() * 0.7f);
            mPlayerNames.add(nameText);
            mSprite.attachChild(nameText);

            Text scoreText = new Text(2 + getWidth() * 0.7f + 5, i * mPlayerInfoHeight, mDispBundle.mGeneralTextFont, "       ", mDispBundle.mVBOM);
            scoreText.setWidth(getWidth() - nameText.getWidth());
            mScoreInfo.add(scoreText);
            mSprite.attachChild(scoreText);
        }
    }

    public void updateScore(ArrayList<Integer> scoreCard)
    {
        for(int i = 0; i < mScoreInfo.size(); i++)
        {
            mScoreInfo.get(i).setText(scoreCard.get(i).toString());
        }

    }
}
