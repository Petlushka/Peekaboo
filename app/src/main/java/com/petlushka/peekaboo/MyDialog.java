package com.petlushka.peekaboo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Irina on 21.12.2015.
 */
public class MyDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Title!").setPositiveButton("Yes", this)
                .setNegativeButton("No", this)
                .setNeutralButton("Maybe", this)
                .setMessage("Congratulations!");
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                Log.d("MyLogs", "Yes");
                break;
            case Dialog.BUTTON_NEGATIVE:
                Log.d("MyLogs", "No");
                break;
            case Dialog.BUTTON_NEUTRAL:
                Log.d("MyLogs", "Maybe");
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("MyLogs", "Dialog : onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("MyLogs", "Dialog : onCancel");
    }
}
