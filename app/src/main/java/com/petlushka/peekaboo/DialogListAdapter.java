package com.petlushka.peekaboo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Irina on 25.01.2016.
 */
public class DialogListAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private int [][] level;
    private int width;

    public DialogListAdapter(Context ctx, int [][] level, int width) {
        this.ctx = ctx;
        this.level = level;
        this.width = width;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return level.length;
    }

    @Override
    public Object getItem(int position) {
        return level[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = lInflater.inflate(R.layout.item_target, parent, false);
        }
        ImageView image = (ImageView)view.findViewById(R.id.ivTarget);
        switch (level[position][0]){
            case 1:
                image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.m1));
                break;
            case 2:
                image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.m2));
                break;
            case 3:
                image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.m3));
                break;
            case 4:
                image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.m4));
                break;
            case 5:
                image.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.m5));
                break;

        }
        int height = width / 6;
        if (ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            height = width / 10;
        image.setMinimumHeight(height);
        image.setMinimumWidth(height);
        image.setMaxHeight(height);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        TextView text = (TextView) view.findViewById(R.id.tvTarget);
        text.setText(" x" + level[position][1]);
        float textHeight = height * 0.4f;
        text.setTextSize(textHeight);
        text.setHeight(height);
        text.setWidth(height);
        text.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Bobblebod.ttf"));
        return view;
    }
}
