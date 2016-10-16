package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.IPlayerFactory;
import com.candyz.a7center.cards.model.Player;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class PlayerFactory implements IPlayerFactory
{
    @Override
    public ArrayList<Player> create(String interactivePlayerId, int numPlayers)
    {
        ArrayList<Player> playerList = createPlayerList(interactivePlayerId, numPlayers);

        Utils.shuffle(playerList);

        return playerList;
    }

    private ArrayList<Player> createPlayerList(String interactivePlayerId, int numPlayers)
    {
        ArrayList<Player> playerList = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++)
        {
            String id = "" + (i + 1);
            playerList.add(new Player(id, "Player " + (i + 1), true, "players/" + (i % 20 + 1) + ".png"));
            if (id.compareTo(interactivePlayerId) == 0)
            {
                playerList.get(i).setInteractive();
                playerList.get(i).attachBrain(new InteractiveBrain());
            }
            else
            {
                playerList.get(i).attachBrain(new SimulatedBrain());
            }
        }
        return playerList;
    }
}
