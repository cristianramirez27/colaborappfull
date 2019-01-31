package com.coppel.rhconecta.dev.views.customviews;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Configuration.LinksNavigation;
import com.coppel.rhconecta.dev.business.models.PushData;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PushInvasiveDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "NotificationDialog";
    public static final String KEY_PUSH_DATA = "_KEY_PUSH_DATA";

    TextView txtTitleNotification;
    TextView txtMessageNotification;
    Button btnOne;
    Button btnTwo;
    Button btnThree;

    private PushData pushData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alert_invasive, container, false);
       // getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        txtTitleNotification = (TextView) rootView.findViewById(R.id.txtTitleNotification);
        txtMessageNotification = (TextView) rootView.findViewById(R.id.txtMessageNotification);
        btnOne = (Button) rootView.findViewById(R.id.btnOne);
        btnTwo = (Button) rootView.findViewById(R.id.btnTwo);
        btnThree = (Button) rootView.findViewById(R.id.btnThree);

        txtTitleNotification.setText(pushData.getTitle());
        txtTitleNotification.setVisibility(pushData.getTitle() != null && !pushData.getTitle().isEmpty() ? VISIBLE : GONE);
        txtMessageNotification.setText(pushData.getSubtitle());
        txtMessageNotification.setLinkTextColor(getResources().getColor(R.color.colorFifthBlueCoppel));

        if(btnOne != null){
            btnOne.setOnClickListener(this);
            btnOne.setVisibility(pushData.getBt1Title() != null && !pushData.getBt1Title().isEmpty() ? VISIBLE : GONE);
            btnOne.setText(pushData.getBt1Title() != null ? pushData.getBt1Title() : "");
        }
        if(btnTwo != null){
            btnTwo.setOnClickListener(this);
            btnTwo.setVisibility(pushData.getBt2Title() != null && !pushData.getBt2Title().isEmpty() ? VISIBLE : GONE);
            btnTwo.setText(pushData.getBt2Title() != null ? pushData.getBt2Title() : "");
        }
        if(btnThree != null){
            btnThree.setOnClickListener(this);
            btnThree.setVisibility(pushData.getBt3Title() != null && !pushData.getBt3Title().isEmpty() ? VISIBLE : GONE);
            btnThree.setText(pushData.getBt3Title() != null ? pushData.getBt3Title() : "");
        }

        return rootView;
    }


    public static PushInvasiveDialog getInstance(PushData pushData){
        PushInvasiveDialog promotionsDialog= new PushInvasiveDialog();
        Bundle args = new Bundle();
        args.putSerializable(PushInvasiveDialog.KEY_PUSH_DATA, pushData);
        promotionsDialog.setArguments(args);

        return promotionsDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pushData = (PushData) getArguments().getSerializable(PushInvasiveDialog.KEY_PUSH_DATA);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
   //     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnOne:

                if(pushData.getBtn1() != null && !pushData.getBtn1().isEmpty())
                    LinksNavigation.navigateTo(getActivity(),pushData.getBtn1());
                break;

            case R.id.btnTwo:
                if(pushData.getBtn2() != null && !pushData.getBtn2().isEmpty())
                    LinksNavigation.navigateTo(getActivity(),pushData.getBtn2());
                break;

            case R.id.btnThree:

                if(pushData.getBtn3() != null && !pushData.getBtn3().isEmpty())
                    LinksNavigation.navigateTo(getActivity(),pushData.getBtn3());
                break;
        }

        dismiss();
    }
}
