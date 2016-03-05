package com.petlushka.peekaboo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Instructions extends Activity implements View.OnClickListener{

    private int page = 1;
    private RelativeLayout view;
    private Button btnBack, btnNext;
    private TextView text;
    private int width;
    private int height;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        width = resolution.x;
        height = resolution.y;
        view = (RelativeLayout)findViewById(R.id.llInstructions);
        view.setBackgroundResource(R.drawable.study1);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnBack.setWidth(width/4);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnNext.setWidth(width/4);
        text = (TextView)findViewById(R.id.tvInstruction);
        text.setHeight(height / 4);
        text.setText(R.string.instruction1);
        text.setTypeface(Typeface.createFromAsset(getAssets(), "Bobblebod.ttf"));
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btnNext:
                if(++page < 6){
                    setPage(page);
                } else {
                    i = new Intent(this, LevelsActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.btnBack:
                if(--page > 0){
                    setPage(page);
                } else {
                    i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
                break;
        }

    }

    public void setPage(int num){
        switch (num){
            case 1:
                view.setBackgroundResource(R.drawable.study1);
                text.setText(R.string.instruction1);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.study2);
                text.setText(R.string.instruction2);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.study3);
                text.setText(R.string.instruction3);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.study4);
                text.setText(R.string.instruction4);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.study5);
                text.setText(R.string.instruction5);
                break;

        }
    }
}
