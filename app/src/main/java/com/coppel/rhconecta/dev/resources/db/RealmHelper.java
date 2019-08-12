package com.coppel.rhconecta.dev.resources.db;

import android.content.Context;
import android.graphics.Bitmap;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;

import java.io.ByteArrayOutputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class RealmHelper {

    public static RealmConfiguration configurateRealm(Context context) {
        return new RealmConfiguration.Builder()
                .name(context.getString(R.string.db_name) + ".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public static void updateMenuItems(String email, List<HomeMenuItem> menuItems) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            UserPreference userPreference = getUserPreferences(email);
            if (userPreference == null) {
                userPreference = realm.createObject(UserPreference.class, email);
            }
            RealmList<HomeMenuItem> realmMenuItems = new RealmList<>();
            for (HomeMenuItem item : menuItems) {
                HomeMenuItem realmItem = realm.copyToRealmOrUpdate(item);
                realmMenuItems.add(realmItem);
            }
            userPreference.setMenuItems(realmMenuItems);
            realm.copyToRealmOrUpdate(userPreference);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public static boolean updateProfileImage(String email, Bitmap image) {
        Realm realm = Realm.getDefaultInstance();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            realm.beginTransaction();
            UserPreference userPreference = getUserPreferences(email);
            if (userPreference == null) {
                userPreference = realm.createObject(UserPreference.class, email);
            }
            userPreference.setImage(bytes);
            realm.copyToRealmOrUpdate(userPreference);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public static UserPreference getUserPreferences(String email) {
        try {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(UserPreference.class).equalTo("email", email).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
