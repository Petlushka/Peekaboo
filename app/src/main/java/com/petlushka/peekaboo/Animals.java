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
        switch (index){
            case 1:
                setBitmapName("elephant");
                break;
            case 2:
                setBitmapName("giraffe");
                break;
            case 3:
                setBitmapName("hippo");
                break;
            case 4:
                setBitmapName("monkey");
                break;
            case 5:
                setBitmapName("zebra");
                break;
        }
    }

    @Override
    public void update() {

    }
}
