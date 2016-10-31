package com.candyz.a7center;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.manager.OptionsManager;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class AvatarSelectorActivity extends AppCompatActivity implements AvatarAdapter.IAvatarSelectionListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_selector);

        OptionsManager.getInstance().init(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photo_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AvatarAdapter(this, this));
    }

    public void onAvatarSelection(String avatarURL)
    {
        Intent data = new Intent();
        data.putExtra("AVATAR_PHOTO", avatarURL);
        if (getParent() == null)
        {
            setResult(Activity.RESULT_OK, data);
        }
        else
        {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }




}
