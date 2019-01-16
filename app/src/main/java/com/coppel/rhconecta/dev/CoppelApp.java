package com.coppel.rhconecta.dev;

import android.app.Application;
import android.content.res.Configuration;

import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import io.realm.Realm;

public class CoppelApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(RealmHelper.configurateRealm(this));
        TextUtilities.adjustFontScale(getApplicationContext(), getResources().getConfiguration());
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // In some cases modifying newConfig leads to unexpected behavior,
        // so it's better to edit new instance.
        Configuration configuration = new Configuration(newConfig);
        TextUtilities.adjustFontScale(getApplicationContext(), configuration);
    }
}
