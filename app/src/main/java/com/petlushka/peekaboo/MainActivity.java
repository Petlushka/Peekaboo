package com.petlushka.peekaboo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKSdk;

public class MainActivity extends Activity implements View.OnClickListener{

    Button btnPlay, btnQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  VKSdk.initialize(this);
        setContentView(R.layout.activity_main);

        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);


        btnQuit = (Button)findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPlay:
                intent = new Intent(this, LevelsActivity.class);
                startActivity(intent);
                break;

            case R.id.btnQuit:
                finish();
                break;
        }

    }
}
