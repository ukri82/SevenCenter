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
    private class Option
    {
        public int mId;
        public String mKey;
        public String mValue;

        public Option(int id, String key, String value)
        {
            mId = id;
            mKey = key;
            mValue = value;
        }
    }
    private Map<String, Option> mOptions = new HashMap<>();

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
        String value = null;
        Option option = mOptions.get(key);
        if(option != null)
        {
            value = option.mValue;
        }
        return value;
    }

    public void set(String key, String value)
    {
        Option option = mOptions.get(key);
        if(option == null)
        {
            int id = (int) mDbHelper.setOption(key, value);
            option = new Option(id, key, value);
            mOptions.put(key, option);
        }
        else
        {
            option.mValue = value;
            mDbHelper.updateOption(option.mId, option.mKey, option.mValue);
        }
    }


    public void readOptions()
    {
        Cursor player = mDbHelper.getOptions();

        if (player != null )
        {
            if  (player.moveToFirst())
            {
                do
                {
                    createOptionEntry(player);
                }
                while (player.moveToNext());
            }
        }
        player.close();
    }

    private void createOptionEntry(Cursor optionCursor)
    {
        int id = optionCursor.getInt(optionCursor.getColumnIndex("id"));
        String key = optionCursor.getString(optionCursor.getColumnIndex("key"));
        String value = optionCursor.getString(optionCursor.getColumnIndex("value"));

        mOptions.put(key, new Option(id, key, value));
    }
}
