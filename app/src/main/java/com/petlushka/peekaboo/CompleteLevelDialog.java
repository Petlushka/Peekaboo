package com.petlushka.peekaboo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Irina on 24.01.2016.
 */
public class CompleteLevelDialog extends Dialog {

    LinearLayout view;
    int level;
    GameView gameView;
    int width;


    public CompleteLevelDialog(Context context, int level, GameView gameView, int width) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.width = width;
        view = (LinearLayout)getLayoutInflater().inflate(R.layout.level_complete_dialog, null);
        Button btnMainMenu = (Button)view.findViewById(R.id.btnMainMenu);
        Button btnNextLevel = (Button)view.findViewById(R.id.btnNextLevel);
        TextView text = (TextView)view.findViewById(R.id.tvWin);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Bobblebod.ttf");
        text.setTypeface(font);
        btnMainMenu.setOnClickListener(myClickListener);
        btnNextLevel.setOnClickListener(myClickListener);
        btnMainMenu.setTypeface(font);
        btnMainMenu.setTextColor(Color.BLACK);
        btnNextLevel.setTypeface(font);
        btnNextLevel.setTextColor(Color.BLACK);
        this.setContentView(view);
        this.level = ++level;
        Log.d("MyLogs", "level " + this.level);
        this.gameView = gameView;
        SharedPreferences sPref = getContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("Level", this.level);
        ed.putBoolean("start", true);
        if(level > sPref.getInt("levelMax", 1)) {
            ed.putInt("levelMax", level);
        }
        for(int j = 1; j <=4; j++) {
            ed.putInt("figure" + j + "position", 0);
            ed.putInt("figure" + j + "rotation", 1);
        }
        ed.commit();
        Log.d("MyLogs", "SPref: level " + sPref.getInt("Level", -1));

    }

    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;

            switch (v.getId()){
                case R.id.btnMainMenu:
                    i = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(i);
                    dismiss();
                    break;
                case R.id.btnNextLevel:
                    if(level <= 48){
                        Log.d("MyLogs", "level " + level);
                        gameView.setLevel(level);
                        gameView.setStartLevel(true);
                        gameView.loadLevel(level);
                        StartLevelDialog dialog = new StartLevelDialog(getContext(), gameView.getTarget(), level, gameView, width);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        i = new Intent(getContext(), LevelsActivity.class);
                        getContext().startActivity(i);
                    }
                    dismiss();
                    break;


            }

        }
    };

}
