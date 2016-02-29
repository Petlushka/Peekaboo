package com.petlushka.peekaboo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by Irina on 18.12.2015.
 */
class Figure extends GameObjects{

    private int rotation;
    private int index;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {

        return position;
    }

    public Figure(int x, int y, int height, int index) {
        setX(x);
        setY(y);
        setWidth(height);
        setHeight(height);
        rotation = 1;
        position = 0;
        this.index = index;
        switch (index){
            case 1:
                setBitmapName("figure1");
                break;
            case 2:
                setBitmapName("figure2");
                break;
            case 3:
                setBitmapName("figure3");
                break;
            case 4:
                setBitmapName("figure4");
                break;
        }
    }

    public Bitmap prepareBitmap(Context context) {
        int resID = context.getResources().getIdentifier(getBitmapName(), "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(),false);
        Matrix matrix = new Matrix();
        switch (getRotation()){
            case 1:
                matrix.postRotate(0);
                break;
            case 2:
                matrix.postRotate(90);
                break;
            case 3:
                matrix.postRotate(180);
                break;
            case 4:
                matrix.postRotate(270);
                break;
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return bitmap;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void rotate(){
        if(index == 1){
            if(rotation == 2){
                rotation = 1;
            } else rotation++;
        } else {
            if(rotation == 4){
                rotation = 1;
            } else {
                rotation++;
            }
        }
    }


    public void move(int x1, int y1, int height) {
        setHeight(height * 9);
        setWidth(height * 9);
        setX(x1 - getHeight() / 2);
        setY(y1 - getHeight() / 2);
    }

}
