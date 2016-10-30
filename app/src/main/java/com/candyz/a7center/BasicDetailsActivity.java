package com.candyz.a7center;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.candyz.a7center.manager.OptionsManager;

public class BasicDetailsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);

        OptionsManager.getInstance().init(this);

        findViewById(R.id.accept_detais).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText edit = (EditText)findViewById(R.id.input_name);
                OptionsManager.getInstance().set("player_name", edit.getText().toString());
                edit = (EditText)findViewById(R.id.input_number);
                OptionsManager.getInstance().set("player_number", edit.getText().toString());
                finish();
            }
        });
    }
}
