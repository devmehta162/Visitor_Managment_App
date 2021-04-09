package com.global.visitormanagment;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialogProgressBar {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialogProgressBar(Activity activity) {
        this.activity = activity;
    }

    public void setLoadingDialog(){

        AlertDialog.Builder builder =new AlertDialog.Builder(activity);

        LayoutInflater inflater =activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.custom_dialog_progress_bar,null));
        builder.setCancelable(false);

        alertDialog =builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
