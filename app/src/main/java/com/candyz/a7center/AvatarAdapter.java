package com.candyz.a7center;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.candyz.a7center.cards.Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by u on 31.10.2016.
 */

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder>
{

    private ArrayList<String> mImageAssets = new ArrayList<>();
    Context mContext;
    IAvatarSelectionListener mAvatarListener;

    public static interface IAvatarSelectionListener
    {
        public void onAvatarSelection(String avatarURL);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        ImageView image;

        public ViewHolder(View v)
        {
            super(v);
            image = (ImageView) v.findViewById(R.id.avatar_image_view);
        }
    }

    public AvatarAdapter(Context c, IAvatarSelectionListener avatarSelectionListener)
    {
        mContext = c;
        mAvatarListener = avatarSelectionListener;
        listAssetFiles("gfx/players");
    }

    private boolean listAssetFiles(String path)
    {

        String [] list;
        try
        {
            list = mContext.getAssets().list(path);
            if (list.length > 0)
            {
                // This is a folder
                for (String file : list)
                {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                }
            }
            else
            {
                mImageAssets.add(path);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public AvatarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        try
        {
            //Bitmap bm = Utils.getBitmapFromAsset(mContext, "gfx/" + OptionsManager.getInstance().get("player_image"));
            final String avatarURL = mImageAssets.get(position);
            Bitmap bm = Utils.getBitmapFromAsset(mContext, avatarURL);
            holder.image.setImageBitmap(bm);
            holder.image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mAvatarListener != null)
                    {
                        mAvatarListener.onAvatarSelection(avatarURL.replace("gfx/",""));
                    }

                }
            });

        }
        catch ( Exception e ){
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return mImageAssets.size();
    }
}