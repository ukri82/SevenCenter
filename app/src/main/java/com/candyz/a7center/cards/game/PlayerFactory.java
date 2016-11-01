package com.candyz.a7center.cards.game;

import android.content.Context;
import android.database.Cursor;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.IPlayerFactory;
import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.db.CBDbInterface;
import com.candyz.a7center.manager.OptionsManager;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by u on 03.10.2016.
 */

public class PlayerFactory implements IPlayerFactory
{
    ArrayList<Player> mPlayerCache;
    CBDbInterface mDbHelper;
    Context mContext;
    int mDifficultyLevel = 2;

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
    public ArrayList<Player> create(int numPlayers, int diffLevel)
    {
        mDifficultyLevel = diffLevel;

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

    private ArrayList<Player> getPlayersOfBrilliance(int numPlayers, int brilliancy, ArrayList<Integer> randomIndices)
    {
        ArrayList<Player> playerList = new ArrayList<>();
        for(int i = 0; i < randomIndices.size(); i++)
        {
            if(mPlayerCache.get(randomIndices.get(i)).getBrilliancy() == brilliancy)
            {
                playerList.add(mPlayerCache.get(randomIndices.get(i)));
                if(playerList.size() == numPlayers)
                {
                    break;
                }
            }

        }
        return playerList;
    }

    private ArrayList<Player> createPlayerList(int numPlayers)
    {

        ArrayList<Player> playerList = new ArrayList<>();

        ArrayList<Integer> randomIndices = getRandomIndices();

        Set<Integer> brillianceLevels = new TreeSet<Integer>();
        brillianceLevels.add(1);brillianceLevels.add(2);brillianceLevels.add(3);

        int requiredPlayers = numPlayers - 1;
        int numPlayersOfLevel = (requiredPlayers + 1) / 2;
        ArrayList<Player> list = getPlayersOfBrilliance(numPlayersOfLevel, mDifficultyLevel, randomIndices);
        playerList.addAll(list);
        brillianceLevels.remove(mDifficultyLevel);
        requiredPlayers -= playerList.size();

        for (int brillianceLevel : brillianceLevels)
        {
            numPlayersOfLevel = (requiredPlayers + 1)/ brillianceLevels.size();
            list = getPlayersOfBrilliance(numPlayersOfLevel, brillianceLevel, randomIndices);

            for(int i = 0; i < list.size(); i++)
            {
                if(playerList.size() < numPlayers - 1)
                    playerList.add(list.get(i));
            }

            if(playerList.size() == numPlayers - 1)
            {
                break;
            }
        }

        for (int i = 0; i < playerList.size(); i++)
        {
            Player p = playerList.get(i);
            p.attachBrain(new SimulatedBrain());
        }

        playerList.add(createInteractivePlayer());

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
