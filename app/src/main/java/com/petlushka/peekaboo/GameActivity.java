package com.petlushka.peekaboo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameActivity extends Activity{

    private GameView gameView;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Log.d("MyLogs", "SPref gameActivity: level " + sPref.getInt("Level", -1));
        int level = sPref.getInt("Level", 1);
        gameView = new GameView(this, resolution.x, resolution.y, level);

        setContentView(gameView);




    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();

    }


}
