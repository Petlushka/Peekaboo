package com.petlushka.peekaboo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Irina on 25.01.2016.
 */
public class StartLevelDialog extends Dialog implements View.OnClickListener{

    private int [][] level;
    private LinearLayout view;
    private Button btnStartLevel;
    private int num;
    private Context context;
    private GameView gm;
    private TextView tvStartDialog;
    private int width;

    public StartLevelDialog(Context context, final int[][] level, int num, GameView gm, int width) {
        super(context);
        this.level = level;
        this.num = num;
        this.context = context;
        this.gm = gm;
        this.width = width;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout)getLayoutInflater().inflate(R.layout.start_level_dialog, null);
        view.setMinimumWidth((int) (this.width * 0.75));


        Typeface font = Typeface.createFromAsset(context.getAssets(), "Bobblebod.ttf");
        tvStartDialog = (TextView)view.findViewById(R.id.tvStartDialog);
        tvStartDialog.setText(getContext().getResources().getText(R.string.level) + " " + num);
        tvStartDialog.setTypeface(font);
        tvStartDialog.setTextSize(width / 10);
        btnStartLevel = (Button)view.findViewById(R.id.btnStartLevel);
        btnStartLevel.setTypeface(font);
        btnStartLevel.setTextColor(Color.BLACK);
        DialogListAdapter adapter = new DialogListAdapter(getContext(), level, width);
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ListView listView = (ListView) view.findViewById(R.id.dialogList);
            listView.setAdapter(adapter);
            btnStartLevel.setTextSize(width / 15);
        } else {
            GridView gridView = (GridView) view.findViewById(R.id.gvDialog);
            gridView.setNumColumns(level.length);
            gridView.setAdapter(adapter);
        }

        btnStartLevel.setMinimumWidth(width / 3);
        btnStartLevel.setOnClickListener(this);

        this.setContentView(view);
        Log.d("MyLogs", "level start " + num);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sPref = getContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("start", false);
        ed.commit();
        gm.setStartLevel(false);
        gm.setLevelComplete(false);
        dismiss();
    }
}
