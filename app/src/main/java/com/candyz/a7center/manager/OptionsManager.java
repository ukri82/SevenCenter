package com.candyz.a7center.manager;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.candyz.a7center.cards.model.Player;
import com.candyz.a7center.db.CBDbInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by u on 30.10.2016.
 */

public class OptionsManager
{
    private Map<String, String> mOptions = new HashMap<>();

    private static final OptionsManager INSTANCE = new OptionsManager();

    public static OptionsManager getInstance()
    {
        return INSTANCE;
    }


    Context mContext;
    CBDbInterface mDbHelper;

    public void init(Context c)
    {
        mContext = c;

        mDbHelper = new CBDbInterface(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        readOptions();
    }

    public void close()
    {
        mDbHelper.close();
    }

    public String get(String key)
    {
        return mOptions.get(key);
    }

    public void set(String key, String value)
    {
        mOptions.put(key, value);
        long retVal = mDbHelper.setOption(key, value);
    }

    public void readOptions()
    {
        Cursor player = mDbHelper.getOptions();

        if (player != null ) {
            if  (player.moveToFirst()) {
                do {
                    createOptionEntry(player);

                }while (player.moveToNext());
            }
        }
        player.close();
    }

    private void createOptionEntry(Cursor option)
    {
        String key = option.getString(option.getColumnIndex("key"));
        String value = option.getString(option.getColumnIndex("value"));

        mOptions.put(key, value);
    }
}
