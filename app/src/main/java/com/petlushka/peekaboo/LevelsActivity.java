package com.petlushka.peekaboo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LevelsActivity extends Activity {


    private int width;
    private int height;
    GridView gridView;
    TextView tvLevels;
    int levelMax;
    SharedPreferences sPref;
    ButtonsAdapter adapter;
    int [] data = new int [48] ;
    int diameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_list);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        width = resolution.x;
        height = resolution.y;
        int buttonWidth = width / 8;
        int buttonHeight = height / 8;
        diameter = Math.min(buttonWidth, buttonHeight);
       //d Log.d("MyLogs", "heightButton = " + heightButton);
        loadMaxLevel();
        for(int i = 0; i < 48; i ++){
            data[i] = i+1;
        }
        gridView = (GridView)findViewById(R.id.gridView);
        adapter = new ButtonsAdapter(this, data, diameter);
        gridView.setAdapter(adapter);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridView.setNumColumns(8);
        } else {
            gridView.setNumColumns(6);
        }
        gridView.setVerticalSpacing(10);
        gridView.setColumnWidth(diameter);
        gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        tvLevels = (TextView)findViewById(R.id.tvLevels);
        tvLevels.setTypeface(Typeface.createFromAsset(this.getAssets(), "Bobblebod.ttf"));
        tvLevels.setTextSize(diameter * 0.7f);
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadMaxLevel();
        if(levelMax != adapter.getLevelMax()){
            adapter = new ButtonsAdapter(this, data, diameter);
            gridView.setAdapter(adapter);
        }
       /* adapter = new ButtonsAdapter(this, data, heightButton);
        gridView.setAdapter(adapter);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridView.setNumColumns(8);
        } else {
            gridView.setNumColumns(6);
        }
        gridView.setVerticalSpacing(10);
       // gridView.setColumnWidth(height);
        gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);*/




    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void loadMaxLevel() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        if(!sPref.contains("levelMax")){
            ed.putInt("levelMax", 1);
            ed.commit();
        }
        levelMax = sPref.getInt("levelMax", 0);

    }


}
