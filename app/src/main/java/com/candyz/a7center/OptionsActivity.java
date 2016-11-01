package com.candyz.a7center;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.manager.OptionsManager;

public class OptionsActivity extends AppCompatActivity
{

    static final int PICK_AVATAR = 1;
    ImageView mAvatarImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        OptionsManager.getInstance().init(this);

        initializeUI();

        findViewById(R.id.accept_detais).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateOptions();
            }
        });
    }

    void initializeUI()
    {
        EditText edit = (EditText)findViewById(R.id.input_name);
        edit.setText(OptionsManager.getInstance().get("player_name"));

        edit = (EditText)findViewById(R.id.input_number);
        edit.setText(OptionsManager.getInstance().get("player_number"));

        updateAvatar();

        CheckBox c = (CheckBox)findViewById(R.id.play_automatic);
        c.setChecked(OptionsManager.getInstance().get("play_automatic").equals("true"));

        c = (CheckBox)findViewById(R.id.pass_automatic);
        c.setChecked(OptionsManager.getInstance().get("pass_automatic").equals("true"));

        final Activity activity = this;
        mAvatarImgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity.startActivityForResult(new Intent(activity, AvatarSelectorActivity.class), PICK_AVATAR);
            }
        });
    }

    void updateOptions()
    {
        EditText edit = (EditText)findViewById(R.id.input_name);
        OptionsManager.getInstance().set("player_name", edit.getText().toString());
        edit = (EditText)findViewById(R.id.input_number);
        OptionsManager.getInstance().set("player_number", edit.getText().toString());

        CheckBox c = (CheckBox)findViewById(R.id.play_automatic);
        String status = "false";
        if(c.isChecked())
            status = "true";
        OptionsManager.getInstance().set("play_automatic", status);

        c = (CheckBox)findViewById(R.id.pass_automatic);
        status = "false";
        if(c.isChecked())
            status = "true";
        OptionsManager.getInstance().set("pass_automatic", status);

        finishCurrentActivity();
    }

    public void finishCurrentActivity()
    {
        Intent data = new Intent();
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

    void updateAvatar()
    {
        mAvatarImgView = (ImageView)findViewById(R.id.input_image);
        Bitmap bm = Utils.getBitmapFromAsset(this, "gfx/" + OptionsManager.getInstance().get("player_image"));
        mAvatarImgView.setImageBitmap(bm);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == PICK_AVATAR)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK)
            {
                OptionsManager.getInstance().set("player_image", data.getStringExtra("AVATAR_PHOTO"));
                updateAvatar();
            }
        }
    }
}
