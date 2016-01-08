package com.petlushka.peekaboo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Irina on 03.01.2016.
 */
public class ButtonsAdapter extends BaseAdapter {

    Context ctx;
    int [] data;
    int diametr;
    int levelMax;
    SharedPreferences sPref;

    public ButtonsAdapter(Context ctx, int [] data, int diametr) {
        this.ctx = ctx;
        this.data = data;
        this.diametr = diametr;
        sPref = ctx.getSharedPreferences("MyPref", ctx.MODE_PRIVATE);
        levelMax = sPref.getInt("levelMax", 0);
    }


    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = new Button(ctx);
            button.setText("" + (data[position]));
        } else {
            button = (Button)convertView;
        }
        if(position >= levelMax){
            button.setEnabled(false);
            button.setAlpha(0.5f);
        }
        button.setId(data[position]);
        button.setTextSize(diametr * 0.2f);
        button.setWidth(diametr);
        button.setHeight(diametr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(ctx.getResources().getDrawable(R.drawable.button));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lv = v.getId();
                Intent intent = new Intent(ctx, GameActivity.class);
                intent.putExtra("level", lv);
                ctx.startActivity(intent);
            }
        });
        return button;
    }

    public void setLevelMax(int levelMax){
        this.levelMax = levelMax;

    }

    public int getLevelMax() {
        return levelMax;
    }
}
