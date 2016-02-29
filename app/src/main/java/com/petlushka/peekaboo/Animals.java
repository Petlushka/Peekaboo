package com.petlushka.peekaboo;

/**
 * Created by Irina on 19.12.2015.
 */
public class Animals extends GameObjects {

    private  int index;

    public Animals(int x, int y, int height, int index) {
        setX(x);
        setY(y);
        setWidth(height);
        setHeight(height);
        this.index = index;
        setBitmapName("m"+index);
    }

}
