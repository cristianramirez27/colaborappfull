package com.coppel.rhconecta.dev.views.customviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.business.Configuration.LinksNavigation;
import com.coppel.rhconecta.dev.business.Configuration.PushSnackBarConfig;
import com.coppel.rhconecta.dev.business.models.PushData;

import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.utils.IUPushNotificationUtil.slideUp;

/**
 * Created by flima on 24/03/2017.
 */

public class PushSnackBarView extends SlideAnimationView {

    private LinearLayout layoutContainer;
    private TextView txtMessage;
    private ImageView imgPuskSnackbar;
    private Context context;
    private PushSnackBarConfig pushSnackBarConfig;
    private Activity activity;

    public PushSnackBarView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PushSnackBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        pushSnackBarConfig = AppConfig.getPushSnackBar(this.context);
        li.inflate(pushSnackBarConfig.getiResLayout(), this, true);
        layoutContainer = ButterKnife.findById(this, R.id.container_notinvasive);
        txtMessage = ButterKnife.findById(this, R.id.txtMessage);
        imgPuskSnackbar = ButterKnife.findById(this, R.id.imgPuskSnackbar);

        imgPuskSnackbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                slideUp(PushSnackBarView.this);

            }
        });

        customize(pushSnackBarConfig);
    }

    public void setPushData(final PushData pushData){
        txtMessage.setText(pushData.getTitle());

        layoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                slideUp(PushSnackBarView.this);

                if(pushData.getBtn1() != null && !pushData.getBtn1().isEmpty())
                    LinksNavigation.navigateTo(activity, pushData.getBtn1());
            }
        });
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void customize(PushSnackBarConfig loaderConfig){



    }
}
