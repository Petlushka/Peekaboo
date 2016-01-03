package com.petlushka.peekaboo;

/**
 * Created by Irina on 22.12.2015.
 */
public class LevelManager {

    private int level;
    private boolean playing;
    LevelData data;
    private int [][] result;
    private int[][] target;

    public LevelManager(int level){
        this.level = level;
        data = new LevelData();
        result = data.result[level - 1];
        target = data.target[level - 1];
    }

    public int[][] getResult() {
        return result;
    }

    public int[][] getTarget() {
        return target;
    }
}
