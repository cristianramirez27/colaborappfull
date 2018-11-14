package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.coppel.rhconecta.dev.R;
import butterknife.ButterKnife;

/**
 * Created by flima on 03/04/2017.
 */

public class SurveyInboxView extends RelativeLayout {

    private ImageView iconSurvey;
    private TextView indicatorInbox;

    public SurveyInboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SurveyInboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.survey_inbox, this, true);
        iconSurvey = ButterKnife.findById(this, R.id.iconoEncuesta);
        indicatorInbox = ButterKnife.findById(this, R.id.txvCenter);
    }

    public void setCountMessages(int numberMessages){
        if(numberMessages > 0){
           // indicatorInbox.setText(String.format("%d",numberMessages) );
            indicatorInbox.setText("");
            indicatorInbox.setVisibility(VISIBLE);
        }else{
            indicatorInbox.setText("");
            indicatorInbox.setVisibility(INVISIBLE);
        }
    }

}
