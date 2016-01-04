package com.petlushka.peekaboo;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_list);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        width = resolution.x;
        height = resolution.y;
        int buttonWidth = width / 6;
        int [] data = new int [48] ;
        for(int i = 0; i < 48; i ++){
            data[i] = i+1;
        }
        gridView = (GridView)findViewById(R.id.gridView);
        ButtonsAdapter adapter = new ButtonsAdapter(this, data);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(6);
        tvLevels = (TextView)findViewById(R.id.tvLevels);
        tvLevels.setTypeface(Typeface.createFromAsset(this.getAssets(), "Bobblebod.ttf"));
        tvLevels.setTextSize(buttonWidth * 0.5f);
    }
}
