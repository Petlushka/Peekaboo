package com.petlushka.peekaboo;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Field;

/**
 * Created by Irina on 18.12.2015.
 */
public class GameView extends SurfaceView implements Runnable{

    private volatile boolean running;
    private Thread gameThread = null ;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    private Context context;
    private int pixelsPerMetreX;
    private int pixelsPerMetreY;
    private int screenXResolution;
    private int screenYResolution;
    private Figure [] figures = new Figure[4];
    private int[][] figurePosition;
    private GameField field;
    private GameField bg;
    private Animals[] animals;
    private int [][] animalsPosition;
    private int startX;
    private int startY;
    private int index = -1;
    private int [][] result;
    private int [][] target;
    private LevelManager lm;
    private int level;
    private SharedPreferences spref;
    private SharedPreferences.Editor ed;
    private Boolean startLevel = true;
    private boolean levelComplete = false;

    public GameView(Context context, int width, int height, int level) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        spref = getContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
        ed = spref.edit();
        screenXResolution = width;
        screenYResolution = height;
        if(isPortraitOrientation()){
            pixelsPerMetreX = screenXResolution / 20;
            pixelsPerMetreY = screenYResolution / 34;
            figurePosition = new int[][]{{1, 30}, {6, 30}, {11, 30}, {16, 30}};
            animalsPosition = new int[][] {{6, 3}, {11, 3}, {8, 6}, {3, 6}, {13, 6}};
        } else {
            pixelsPerMetreX = screenXResolution / 34;
            pixelsPerMetreY = screenYResolution / 20;
            figurePosition = new int[][]{{29, 1}, {29, 6}, {29, 11}, {29, 16}};
            animalsPosition = new int[][] {{2, 5}, {2, 8}, {2, 11}, {2, 14}, {2, 17}};
        }
        this.level = spref.getInt("Level", level);
        startLevel = spref.getBoolean("start", true);
        loadLevel(this.level);
        Log.d("MyLogs", "SPref gameView: level " + spref.getInt("Level", -1));


    }

    @Override
     public void run() {
        while (running){
            update();
            draw();
        }

    }

    private void draw() {
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawBitmap(bg.prepareBitmap(getContext()), 0, 0, paint);
            if(startLevel == true || levelComplete == true) {

            } else {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.YELLOW);
                paint.setTextSize(45);
                paint.setStyle(Paint.Style.STROKE);
                paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Bobblebod.ttf"));
                if (isPortraitOrientation()) {
                    canvas.drawText("LEVEL " + level, 10 * pixelsPerMetreX, 2 * pixelsPerMetreY, paint);
                } else {
                    canvas.drawText("LEVEL " + level, 4 * pixelsPerMetreX, 3 * pixelsPerMetreY, paint);
                }
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(40);
                canvas.drawBitmap(field.prepareBitmap(getContext()), field.getX(), field.getY(), paint);
                for (int i = 0; i < animals.length; i++) {
                    canvas.drawBitmap(animals[i].prepareBitmap(getContext()), animals[i].getX(), animals[i].getY(), paint);
                    canvas.drawText(" x " + target[i][1], animals[i].getX() + animals[i].getWidth(), animals[i].getY() + animals[i].getHeight() / 2, paint);
                }

                for (int i = 0; i < figures.length; i++) {
                    canvas.drawBitmap(figures[i].prepareBitmap(getContext()), figures[i].getX(), figures[i].getY(), paint);

                }
            }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void update(){
        try{
            gameThread.join(17);
        } catch (InterruptedException e){
            Log.e("error", "failed to update thread");
        }
    }

    public void pause() {
        running = false;
        save();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
        if(spref.contains("Level"))
            level = spref.getInt("Level", 1);
        startLevel = spref.getBoolean("start", false);
        loadLevel(level);
        load();
        if (startLevel == true){
            int height = Math.min(screenXResolution, screenYResolution);
            StartLevelDialog startDialog = new StartLevelDialog(context, target, spref.getInt("Level", -1), this, height);
            startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            startDialog.setCancelable(false);
            startDialog.show();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int)event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    for (int i = 0; i < 4; i++) {
                        if (figures[i].getX() < startX && startX < figures[i].getX() + figures[i].getWidth() && figures[i].getY() < startY && startY < figures[i].getY() + figures[i].getHeight()) {
                            index = i;
                            break;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(index >= 0 && Math.abs(x - startX) >= 5 && Math.abs(y - startY) >= 5)
                        figures[index].move(x, y, pixelsPerMetreX);
                    break;
                case MotionEvent.ACTION_UP:
                    if (index >= 0) {
                        if (Math.abs(x - startX) < pixelsPerMetreX && Math.abs(y - startY) < pixelsPerMetreY) {
                            figures[index].rotate();
                        }
                            int x1 = field.getX();
                            int y1 = field.getY();
                            int x2 = figures[index].getX();
                            int y2 = figures[index].getY();
                            int half = field.getHeight() / 2;
                            if (x1 - pixelsPerMetreX < x2 && x2 < x1 + pixelsPerMetreX && y1 - pixelsPerMetreY < y2 && y2 < y1 + pixelsPerMetreY) {
                                figures[index].setX(x1);
                                figures[index].setY(y1);
                                figures[index].setPosition(1);
                            } else if (x1 + half - pixelsPerMetreX < x2 && x2 < x1 + half + pixelsPerMetreX && y1 - pixelsPerMetreY < y2 && y2 < y1 + pixelsPerMetreY) {
                                figures[index].setX(x1 + half);
                                figures[index].setY(y1);
                                figures[index].setPosition(2);
                            } else if (x1 - pixelsPerMetreX < x2 && x2 < x1 + pixelsPerMetreX && y1 + half - pixelsPerMetreY < y2 && y2 < y1 + half + pixelsPerMetreY) {
                                figures[index].setX(x1);
                                figures[index].setY(y1 + half);
                                figures[index].setPosition(3);
                            } else if (x1 + half - pixelsPerMetreX < x2 && x2 < x1 + half + pixelsPerMetreX && y1 + half - pixelsPerMetreY < y2 && y2 < y1 + half + pixelsPerMetreY) {
                                figures[index].setX(x1 + half);
                                figures[index].setY(y1 + half);
                                figures[index].setPosition(4);
                            } else {
                                figures[index].setHeight(3 * pixelsPerMetreX);
                                figures[index].setWidth(3 * pixelsPerMetreX);
                                figures[index].setX(figurePosition[index][0] * pixelsPerMetreX);
                                figures[index].setY(figurePosition[index][1] * pixelsPerMetreY);
                                figures[index].setPosition(0);
                            }

                    }
                    startX = -1;
                    startY = -1;
                    index = -1;
                    if(isCorrectSolution()){
                        try {
                            gameThread.join(2000);
                            levelComplete = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        int height = Math.min(screenXResolution, screenYResolution);
                        CompleteLevelDialog finish_dialog = new CompleteLevelDialog(context, level, this, height);
                        finish_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        finish_dialog.setCancelable(false);
                        finish_dialog.show();
                    }

                    break;
            }

        return true;
    }

    public  boolean isCorrectSolution(){
        boolean solution =true;
        for(int i = 0; i < figures.length; i++){
            if(figures[i].getPosition() == result[i][0] && figures[i].getRotation() == result[i][1]){
                continue;
            }
            solution = false;
            break;
        }
        return solution;
    }

    public void loadLevel(int level){
        lm = null;
        lm = new LevelManager(level);
        result = lm.getResult();
        target = lm.getTarget();
        for (int i = 0; i < 4; i++) {
            figures[i] = new Figure(figurePosition[i][0] * pixelsPerMetreX, figurePosition[i][1] * pixelsPerMetreY, 3 * pixelsPerMetreX, i+1);
        }
        bg = new GameField(0, 0, screenYResolution);
        bg.setWidth(screenXResolution);
        if(!isPortraitOrientation()) {
            field = new GameField(8 * pixelsPerMetreX, pixelsPerMetreY, 18 * pixelsPerMetreX);
            bg.setBitmapName("background_land");
        } else {
            field = new GameField(pixelsPerMetreX, 9 * pixelsPerMetreY, 18 * pixelsPerMetreX);
            bg.setBitmapName("background");
        }
        animals = new Animals[target.length];
        for(int i = 0; i < animals.length; i++){
            animals[i] = new Animals(animalsPosition[i][0]*pixelsPerMetreX, animalsPosition[i][1] * pixelsPerMetreY, 2 * pixelsPerMetreX, target[i][0]);
        }
    }

    private boolean isPortraitOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return true;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return false;
        else
            return true;
    }

    public void save(){

        ed.putInt("Level", level);
        for(int i = 1; i <=4; i++) {
            ed.putInt("figure" + i + "position", figures[i - 1].getPosition());
            ed.putInt("figure" + i + "X", figures[i - 1].getX());
            ed.putInt("figure" + i + "Y", figures[i-1].getY());
            ed.putInt("figure" + i + "rotation", figures[i-1].getRotation());
            Log.d("MyLogs", "save figure " + i + " - " + figures[i - 1].getPosition() + " " + figures[i-1].getRotation());
        }
        ed.commit();
    }

    public void load(){
        int halfWidth = field.getWidth() / 2;
        int [][] positions = new int [][]{{field.getX(), field.getY()},
                {field.getX() + halfWidth, field.getY()},
                {field.getX(), field.getY() + halfWidth},
                {field.getX() + halfWidth, field.getY() + halfWidth} };
        for(int i = 1; i <= 4; i++) {
            figures[i-1].setPosition(spref.getInt("figure" + i + "position", 0));
            figures[i-1].setRotation(spref.getInt("figure" + i + "rotation", 1));

            int pos = figures[i-1].getPosition();
            if(pos > 0){
                figures[i-1].setX(positions[pos-1][0]);
                figures[i-1].setY(positions[pos-1][1]);
                figures[i-1].setHeight(9 * pixelsPerMetreX);
                figures[i-1].setWidth(9 * pixelsPerMetreX);
            }
        }


    }

    public int[][] getTarget() {
        return target;
    }

    public void setStartLevel(Boolean startLevel) {

        this.startLevel = startLevel;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
