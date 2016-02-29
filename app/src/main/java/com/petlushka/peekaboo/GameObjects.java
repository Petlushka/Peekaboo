package com.petlushka.peekaboo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Irina on 18.12.2015.
 */
abstract class GameObjects{

    private int width;
    private int height;
    private int x;
    private int y;
    private String bitmapName;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height,false);
        return bitmap;
    }
    public void setBitmapName(String bitmapName){
        this.bitmapName = bitmapName;
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
