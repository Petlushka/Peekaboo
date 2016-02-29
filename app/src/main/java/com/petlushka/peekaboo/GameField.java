package com.petlushka.peekaboo;

/**
 * Created by Irina on 19.12.2015.
 */
class GameField extends GameObjects {

    public GameField(int x, int y, int height) {
        setX(x);
        setY(y);
        setWidth(height);
        setHeight(height);
        setBitmapName("gamefield");
    }

}
