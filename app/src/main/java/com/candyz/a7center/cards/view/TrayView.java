package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Tray;

/**
 * Created by u on 04.10.2016.
 */

public class TrayView extends BaseView
{
    Tray mTray;

    public TrayView(Tray t, DisplayBundle d)
    {
        super("tray.png", d);

        mTray = t;

        show();
    }

    public void add(CardView cardView)
    {
        float posX = getX() + getWidth() / 2 + (cardView.getCard().getSuit() - 2) * (cardView.getWidth() + 5);
        float posY = getY() + getHeight() / 2 + (cardView.getCard().getNumber() - 7) * 20;

        if(!cardView.isOpen())
            cardView.open();
        cardView.setPosition(posX, posY);
        cardView.getCard().played(true);
    }

}
