package com.petlushka.peekaboo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LevelsActivity extends Activity implements View.OnClickListener {

    RelativeLayout relLayout;
    private int width;
    private int height;
    private int maxLevel = 48;
    private int levelNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        width = resolution.x;
        height = resolution.y;
        relLayout = (RelativeLayout)findViewById(R.id.relLayout);
        int buttonWidth = width /7;
    //    RelativeLayout.LayoutParams lpView = new RelativeLayout.LayoutParams(buttonWidth, buttonWidth);
        int xButton = 0;
        int yButton = buttonWidth /2;

        for(int i = 1; i < 49; i++){
            Button button = new Button(this);
            button.setText("" + i);
            button.setPadding(2,2,2,2);
            relLayout.addView(button, buttonWidth, buttonWidth);
            if((xButton + buttonWidth *2) >= width){
                xButton = 0;
                yButton += buttonWidth;
            }
            button.setX(xButton);
            button.setY(yButton);
            button.setTag(i);
            button.setOnClickListener(this);
            if(i > maxLevel) {
                button.setEnabled(false);
            }
            xButton += buttonWidth;
        }


    }

    @Override
    public void onClick(View v) {
        int lv = (int)v.getTag();
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("level", lv);
        startActivity(intent);
    }
}
