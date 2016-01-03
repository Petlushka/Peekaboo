package com.petlushka.peekaboo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Irina on 18.12.2015.
 */
public class GameView extends SurfaceView implements Runnable{

    private volatile boolean running;
    Thread gameThread = null ;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    Matrix matrix;

    Context context;
    private int pixelsPerMetreX;
    private int pixelsPerMetreY;
    private int screenXResolution;
    private int screenYResolution;
    Figure [] figures = new Figure[4];
    int[][] figurePosition = {{1, 30}, {6, 30}, {11, 30}, {16, 30}};
    GameField field;
    Animals[] animals;
    int [][] animalsPosition = {{6, 3}, {11, 3}, {8, 6}, {3, 6}, {13, 6}};
    int startX;
    int startY;
    int index = -1;
    int [][] result;
    int [][] target;
    LevelManager lm;
    int level;

    public GameView(Context context, int width, int height, int level) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        screenXResolution = width;
        screenYResolution = height;
        pixelsPerMetreX = screenXResolution / 20;
        pixelsPerMetreY = screenYResolution / 34;


        this.level = level;
        loadLevel(level);



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
            canvas.drawColor(Color.YELLOW);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Bobblebod.ttf"));
            canvas.drawText("LEVEL " + level, 10 * pixelsPerMetreX, 2 * pixelsPerMetreY, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(35);
            canvas.drawBitmap(field.prepareBitmap(getContext()), field.getX(), field.getY(), paint);
            for (int i = 0; i < animals.length; i++) {
                    canvas.drawBitmap(animals[i].prepareBitmap(getContext()), animals[i].getX(), animals[i].getY(), paint);
                    canvas.drawText(" x " + target[i][1], animals[i].getX() + animals[i].getWidth(), animals[i].getY() + animals[i].getHeight() / 2, paint);
            }

            for (int i = 0; i < figures.length; i++) {
                canvas.drawBitmap(figures[i].prepareBitmap(getContext()), figures[i].getX(), figures[i].getY(), paint);

            }
            if(isCorrectSolution()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.BLACK);
                paint.setTextSize(50);
                paint.setStyle(Paint.Style.STROKE);
                paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Bobblebod.ttf"));
                canvas.drawText("Congratulations!!!", 10 * pixelsPerMetreX, 30 * pixelsPerMetreY, paint);
                int resID = context.getResources().getIdentifier("button", "drawable", context.getPackageName());
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
                bitmap = Bitmap.createScaledBitmap(bitmap, 6 * pixelsPerMetreX,  2 * pixelsPerMetreY, false);
                canvas.drawBitmap(bitmap, 7 * pixelsPerMetreX, 31 * pixelsPerMetreY, paint);

            }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void update(){
        try{
            gameThread.join(17);
        } catch (InterruptedException e){

        }
    }

    public void pause() {
        running = false;
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

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d("MyLogs"," touch ");
        int x = (int) event.getX();
        int y = (int)event.getY();
        if(!isCorrectSolution()) {
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
                    //Log.d("MyLogs", "x = " + startX + ", y = " + startY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(index >= 0 && Math.abs(x - startX) >= 5 && Math.abs(y - startY) >= 5)
                        figures[index].move(x, y, pixelsPerMetreX);
                    //Log.d("MyLogs", "index = " + index);

                    break;
                case MotionEvent.ACTION_UP:
                    if (index >= 0) {
                        if (Math.abs(x - startX) < 5 && Math.abs(y - startY) < 5) {
                            figures[index].rotate();
                        }

                            int x1 = field.getX();
                            int y1 = field.getY();
                            int x2 = figures[index].getX();
                            int y2 = figures[index].getY();
                            Log.d("MyLogs", "x1 =" + x1 + ", x2 = " + x2 + ", y1 = " + y1 + ", y2 =" + y2);
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

                    } else {
                        Log.d("MyLogs", "nothing");
                    }

                    startX = -1;
                    startY = -1;
                    index = -1;

                    break;
            }
        } else {
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    if(level < 48){
                        level++;
                        loadLevel(level);
                    } else {

                    }

            }
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
        field = new GameField(pixelsPerMetreX, 9 * pixelsPerMetreY, 18 * pixelsPerMetreX);
        animals = new Animals[target.length];
        for(int i = 0; i < animals.length; i++){
            animals[i] = new Animals(animalsPosition[i][0]*pixelsPerMetreX, animalsPosition[i][1] * pixelsPerMetreY, 2 * pixelsPerMetreX, target[i][0]);
        }
    }

}
