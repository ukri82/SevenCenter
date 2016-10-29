package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Tray;

import org.andengine.util.modifier.ease.EaseBounceOut;

/**
 * Created by u on 04.10.2016.
 */

public class TrayView extends BaseView
{
    Tray mTray;

    public TrayView(Tray t, DisplayBundle d)
    {
        super("tray.png", 1024, 1024, d);

        mTray = t;

        show();
    }


    public void add(CardView cardView)
    {
        float cardHorizontalGap = 5;
        float cardMargin = 40;
        float cardHeight = (getHeight() - cardMargin) / 3.4f; //  12 * 0.2x + x = total height
        float  cardVerticalGap = cardHeight * 0.2f;
        cardView.setWidth(cardView.getWidth() * cardHeight / cardView.getHeight());
        cardView.setHeight(cardHeight);

        float posX = getX() + getWidth() / 2 + (cardView.getCard().getSuit() - 3) * (cardView.getWidth() + cardHorizontalGap);
        float posY = getY() + getHeight() / 2 + cardVerticalGap / 2 + (cardView.getCard().getNumber() - 7 - 4) * cardVerticalGap;

        cardView.removeHighlight();
        if(!cardView.isOpen())
            cardView.open();
        cardView.animateMove(posX, posY);
        cardView.getCard().played(true);
    }



}
