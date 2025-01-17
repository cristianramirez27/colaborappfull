package com.coppel.rhconecta.dev.resources.db;

import android.content.Context;
import android.graphics.Bitmap;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.NotificationsUser;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.resources.db.models.MainSection;
import com.coppel.rhconecta.dev.resources.db.models.SectionDb;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmHelper {

    public static RealmConfiguration configurateRealm(Context context) {
        return new RealmConfiguration.Builder()
                .name(context.getString(R.string.db_name) + ".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public static <T extends RealmObject> void insertOrUpdate(List<T> list) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.insert(list);
            realm.commitTransaction();
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    public static synchronized List<MainSection> getListSection() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
        RealmResults results = realm.where(MainSection.class).findAll();
        List<MainSection> list = new ArrayList<>();
        if (results != null) {
            list = realm.copyFromRealm(results);
        }
        realm.close();
        return list;
    }

    public static synchronized List<SectionDb> getListSubSection() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
        RealmResults results = realm.where(SectionDb.class).findAll();
        List<SectionDb> list = new ArrayList<>();
        if (results != null) {
            list = realm.copyFromRealm(results);
        }
        realm.close();
        return list;
    }

    public static void deleteSection() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
        realm.beginTransaction();
        realm.where(MainSection.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteSubSection() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
        realm.beginTransaction();
        realm.where(SectionDb.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
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
            //e.printStackTrace();
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
            //e.printStackTrace();
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
            //e.printStackTrace();
            return null;
        }
    }

    public static List<NotificationsUser> getNotifications(String user) {
        try {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(NotificationsUser.class).equalTo("USER_NUMBER", user).findAll();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static void deleteNotifications(String user,int IdSistema) {
        try {
            Realm realm = Realm.getDefaultInstance();

            List<NotificationsUser> notificationsUsers =  realm.where(NotificationsUser.class).equalTo("USER_NUMBER", user).findAll();
            realm.beginTransaction();
            for(NotificationsUser notificationsUser : notificationsUsers){
                if(notificationsUser.getID_SISTEMA() == IdSistema)
                    notificationsUser.deleteFromRealm();
            }

            realm.commitTransaction();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
