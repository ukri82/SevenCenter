package com.candyz.a7center.cards.game;

import android.content.Context;
import android.database.Cursor;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.IPlayerFactory;
import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.db.CBDbInterface;
import com.candyz.a7center.manager.OptionsManager;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class PlayerFactory implements IPlayerFactory
{
    ArrayList<Player> mPlayerCache;
    CBDbInterface mDbHelper;
    Context mContext;

    public PlayerFactory(Context context)
    {
        mContext = context;

        mDbHelper = new CBDbInterface(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        init();
    }

    public void close()
    {
        mDbHelper.close();
    }


    @Override
    public ArrayList<Player> create(int numPlayers)
    {
        ArrayList<Player> playerList = createPlayerList(numPlayers);

        Utils.shuffle(playerList);

        return playerList;
    }

    ArrayList<Integer> getRandomIndices()
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < mPlayerCache.size(); i++)
        {
            numbers.add(i);
        }
        Utils.shuffle(numbers);
        return numbers;
    }

    private ArrayList<Player> createPlayerList(int numPlayers)
    {

        ArrayList<Player> playerList = new ArrayList<>();

        playerList.add(createInteractivePlayer());

        ArrayList<Integer> randomIndices = getRandomIndices();

        for(int i = 0; i < numPlayers - 1; i++)
        {
            Player p = mPlayerCache.get(randomIndices.get(i));
            p.attachBrain(new SimulatedBrain());
            playerList.add(p);
        }

        return playerList;
    }


    public void init()
    {
        Cursor player = mDbHelper.getPlayers();

        mPlayerCache = new ArrayList<>();
        if (player != null ) {
            if  (player.moveToFirst()) {
                do {
                    mPlayerCache.add(createPlayer(player));

                }while (player.moveToNext());
            }
        }
        player.close();
    }

    private Player createPlayer(Cursor player)
    {
        int anId = player.getInt(player.getColumnIndex("id"));
        String name = player.getString(player.getColumnIndex("player_name"));
        int gender = player.getInt(player.getColumnIndex("gender"));
        String playerURL = "players/" + player.getString(player.getColumnIndex("photo_url"));
        int brilliancy = player.getInt(player.getColumnIndex("brilliancy"));
        return new Player(anId + "", name, gender == 0, playerURL, brilliancy);
    }

    private Player createInteractivePlayer()
    {
        OptionsManager.getInstance().readOptions();
        String interactivePlayerName = OptionsManager.getInstance().get("player_name");
        String interactivePlayerId = OptionsManager.getInstance().get("player_id");
        String interactivePlayerPhoto = OptionsManager.getInstance().get("player_image");

        Player interactivePlayer = new Player(interactivePlayerId, interactivePlayerName, true, interactivePlayerPhoto, 3);
        interactivePlayer.setInteractive();
        interactivePlayer.attachBrain(new InteractiveBrain());
        return interactivePlayer;
    }
}
