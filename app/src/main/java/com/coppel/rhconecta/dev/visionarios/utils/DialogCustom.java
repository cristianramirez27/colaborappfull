package com.coppel.rhconecta.dev.visionarios.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;

public class DialogCustom {
    private  Dialog dialog;
    private int idLayoutDialog;
    private Activity activityDialog;

    public DialogCustom() {

    }

    public DialogCustom(Activity activityDialog,int idLayoutDialog ) {
        this.idLayoutDialog = idLayoutDialog;
        this.activityDialog = activityDialog;
    }

    public void showDialog(String msg){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public void showDialogActionNoButton(String msg, String msg2, View.OnClickListener action){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        TextView text2 = (TextView) dialog.findViewById(R.id.text_dialog2);
        text2.setText(msg2);


        RelativeLayout containerDialog = (RelativeLayout) dialog.findViewById(R.id.containerDialog);

        containerDialog.setOnClickListener(action);

        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void showDialogNoAction(String msg,String msg2){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        TextView text2 = (TextView) dialog.findViewById(R.id.text_dialog2);
        text2.setText(msg2);


        RelativeLayout containerDialog = (RelativeLayout) dialog.findViewById(R.id.containerDialog);

        containerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }
    public void showDialog( String msg, View.OnClickListener clickOkMethod){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(clickOkMethod);


        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }


    public void showDialog( String msg, View.OnClickListener clickMethod,View.OnClickListener clickCloseMethod){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(clickMethod);


        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(clickCloseMethod );


        dialog.show();

    }


    public void showDialog(int iconid, String msg, View.OnClickListener clickMethod,View.OnClickListener clickCloseMethod){
        dialog = new Dialog(activityDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(idLayoutDialog);

        if(iconid==0){
            ImageView imgIconDialog = (ImageView) dialog.findViewById(R.id.imgIconDialog);
            imgIconDialog.setVisibility(View.GONE);
        }else{
            ImageView imgIconDialog = (ImageView) dialog.findViewById(R.id.imgIconDialog);
            imgIconDialog.setImageResource(iconid);
        }


        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(clickMethod);


        ImageView clodeButton = (ImageView) dialog.findViewById(R.id.imgClose);
        clodeButton.setOnClickListener(clickCloseMethod );


        dialog.show();

    }

    public  void CloseDialog(){
        this.dialog.dismiss();
    }
}

