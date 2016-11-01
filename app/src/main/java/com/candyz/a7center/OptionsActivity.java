package com.candyz.a7center;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.manager.OptionsManager;

public class OptionsActivity extends AppCompatActivity
{

    static final int PICK_AVATAR = 1;
    ImageView mAvatarImgView;

    String mNumPlayersUpdated = "false";
    String mDiffLevelUpdated = "false";

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

        Spinner numPlayersSpinner = (Spinner)findViewById(R.id.input_number_of_players);
        String[] items = new String[]{"3", "4", "5", "6", "7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.num_players_item, items);
        numPlayersSpinner.setAdapter(adapter);
        int numPlayers = Integer.parseInt(OptionsManager.getInstance().get("number_of_players"));
        numPlayersSpinner.setSelection(numPlayers - 3);

        RadioGroup radio = (RadioGroup)findViewById(R.id.input_difficulty_level);
        int radioId = R.id.input_medium;
        String diffLevel = OptionsManager.getInstance().get("difficulty_level");
        if(diffLevel.equals("1"))
        {
            radioId = R.id.input_medium;
        }
        else if(diffLevel.equals("3"))
        {
            radioId = R.id.input_hard;
        }
        radio.check(radioId);
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

        Spinner numPlayersSpinner = (Spinner)findViewById(R.id.input_number_of_players);
        String selectedValue = numPlayersSpinner.getSelectedItem().toString();
        if(!selectedValue.equals(OptionsManager.getInstance().get("number_of_players")))
        {
            OptionsManager.getInstance().set("number_of_players", selectedValue);
            mNumPlayersUpdated = "true";
        }

        RadioGroup radio = (RadioGroup)findViewById(R.id.input_difficulty_level);
        int diffLevel = radio.indexOfChild(findViewById(radio.getCheckedRadioButtonId())) + 1;
        if(!OptionsManager.getInstance().get("difficulty_level").equals(diffLevel + ""))
        {
            OptionsManager.getInstance().set("difficulty_level", diffLevel + "");
            mDiffLevelUpdated = "true";
        }

        finishCurrentActivity();
    }

    public void finishCurrentActivity()
    {
        Intent data = new Intent();
        data.putExtra("DIFF_LEVEL_UPDATED", mDiffLevelUpdated);
        data.putExtra("NUM_PLAYERS_UPDATED", mNumPlayersUpdated);
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
