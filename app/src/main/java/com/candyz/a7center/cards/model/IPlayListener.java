package com.candyz.a7center.cards.model;

/**
 * Created by u on 05.10.2016.
 */

public interface IPlayListener
{
    public void beforePlay(final int playerIndex);
    public void play(int playerIndex, Card c);
}
