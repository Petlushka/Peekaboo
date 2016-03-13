package com.petlushka.peekaboo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by Irina on 03.01.2016.
 */
public class ButtonsAdapter extends BaseAdapter {

    private Context ctx;
    private int [] data;
    private int diametr;
    private int levelMax;
    private SharedPreferences sPref;

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
            button.setBackground(ctx.getApplicationContext().getResources().getDrawable(R.drawable.button_sm));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lv = v.getId();
                Intent intent = new Intent(ctx, GameActivity.class);
                SharedPreferences spref = ctx.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = spref.edit();
                ed.putInt("Level", lv);
                ed.putBoolean("start", true);
                for(int i = 1; i <=4; i++) {
                    ed.putInt("figure" + i + "position", 0);
                    ed.putInt("figure" + i + "rotation", 1);
                }
                ed.commit();
                ctx.startActivity(intent);
            }
        });
        return button;
    }

    public int getLevelMax() {
        return levelMax;
    }
}
