package com.candyz.a7center.db;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Dell on 4/23/2016.
 */


public class CBDbInterface
{
    protected static final String TAG = "CBDbInterface";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public CBDbInterface(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public CBDbInterface createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public CBDbInterface open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getPlayers()
    {
        String filterType;
        String[] params = new String[]{};

        String sql = "SELECT *  FROM player";
        return rawExecute(sql, params);
    }

    public Cursor getOptions()
    {
        String[] params = new String[]{};

        String sql = "SELECT *  FROM option";
        return rawExecute(sql, params);
    }

    public long setOption(String key, String value)
    {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);

        return mDb.insert("option", null, values);
    }

    public long updateOption(int id, String key, String value)
    {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);

        return mDb.update("option", values, "id="+id, null);
    }

    public Cursor rawExecute(String sql, String[] params)
    {
        try
        {
            Cursor mCur = mDb.rawQuery(sql, params);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "rawExecute >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
