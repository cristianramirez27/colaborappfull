package com.coppel.rhconecta.dev;

import android.content.Context;
import android.content.res.Configuration;
import androidx.multidex.MultiDexApplication;

import com.coppel.rhconecta.dev.business.utils.Foreground;
import com.coppel.rhconecta.dev.di.AppModuleKt;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.crashes.Crashes;

import io.realm.Realm;

public class CoppelApp extends MultiDexApplication {

    private static Context contextApp;

    private static CoppelApp mInstance;

    private boolean test = false;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Foreground.init(this);
        initAppCenter();
        AppModuleKt.initKoin(this);
        Realm.setDefaultConfiguration(RealmHelper.configurateRealm(this));
        TextUtilities.adjustFontScale(getApplicationContext(), getResources().getConfiguration());
        contextApp = this;
        mInstance = this;

    }

    /**
     *
     */
    private void initAppCenter() {
        AppCenter.start(this, getString(R.string.app_center_id), Crashes.class);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // In some cases modifying newConfig leads to unexpected behavior,
        // so it's better to edit new instance.
        Configuration configuration = new Configuration(newConfig);
        TextUtilities.adjustFontScale(getApplicationContext(), configuration);
    }

    public static Context getContext(){
        return contextApp;
    }

    public static CoppelApp getInstance() {
        return mInstance;
    }




}
