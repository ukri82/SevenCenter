package com.candyz.a7center.cards.model;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public interface IPlayerFactory
{
    public ArrayList<Player> create(String interactivePlayerId, int numPlayers);
}
