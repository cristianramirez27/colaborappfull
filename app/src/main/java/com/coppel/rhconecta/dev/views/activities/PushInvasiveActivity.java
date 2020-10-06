package com.coppel.rhconecta.dev.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.PushData;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;

import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.customviews.PushInvasiveDialog.KEY_PUSH_DATA;


/**
 * Created by flima on 18/01/2018.
 */

public class PushInvasiveActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    public final static String SHOW_PUSH_EVENT = "_SHOW_PUSH_EVENT";
    private PushData pushData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.invasive_container);
        initViews();
        //ToolbarUI.setStatusBarTranslucent(this,true);
        if(getIntent().hasExtra(KEY_PUSH_DATA)){
            pushData = (PushData) IntentExtension.getSerializableExtra(getIntent(), KEY_PUSH_DATA);
           /* PushInvasiveDialog pushInvasiveDialog = PushInvasiveDialog.getInstance((PushData)data);
            pushInvasiveDialog.setCancelable(true);
            pushInvasiveDialog.show(getSupportFragmentManager(),PushInvasiveDialog.TAG);*/

        }else {
            finish();
        }
    }

    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
