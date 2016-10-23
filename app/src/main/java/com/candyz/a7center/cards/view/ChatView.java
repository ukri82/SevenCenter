package com.candyz.a7center.cards.view;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;

/**
 * Created by u on 17.10.2016.
 */

public class ChatView extends BaseView
{
    Text mStatusText;
    String mMaxString = new String();

    public ChatView(DisplayBundle dispBundle)
    {
        super("chat_bg.png", 1024, 1024, dispBundle);
        mMaxString = "                                                                                          ";
        mStatusText = new Text(5, 5, mDispBundle.mGeneralTextFont, mMaxString, mDispBundle.mVBOM);


    }

    public void show()
    {
        super.show();
        mSprite.attachChild(mStatusText);
    }

    public void addStatus(String statusText)
    {
        if(statusText.length() > mMaxString.length())
        {
            int toTrim = mMaxString.length() - statusText.length() + 1;
            statusText = statusText.substring(0, toTrim);
        }
        mStatusText.setText(statusText);
    }

}
