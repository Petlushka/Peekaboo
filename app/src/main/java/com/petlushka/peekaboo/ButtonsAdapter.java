package com.petlushka.peekaboo;

import android.content.Context;
import android.content.Intent;
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

    public ButtonsAdapter(Context ctx, int [] data) {
        this.ctx = ctx;
        this.data = data;
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
        button.setId(data[position]);
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

}
