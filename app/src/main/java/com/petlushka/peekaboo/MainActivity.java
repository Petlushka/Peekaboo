package com.petlushka.peekaboo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKSdk;

public class MainActivity extends Activity implements View.OnClickListener{

    Button btnPlay, btnQuit, btnInstruction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset(getAssets(), "Bobblebod.ttf");
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        btnPlay.setTypeface(font);


        btnQuit = (Button)findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(this);
        btnQuit.setTypeface(font);

        btnInstruction = (Button)findViewById(R.id.btnInstruction);
        btnInstruction.setOnClickListener(this);
        btnInstruction.setTypeface(font);


    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPlay:
                intent = new Intent(this, LevelsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnInstruction:
                intent = new Intent(this, Instructions.class);
                startActivity(intent);
                break;
            case R.id.btnQuit:
                finish();
                break;
        }

    }
}
