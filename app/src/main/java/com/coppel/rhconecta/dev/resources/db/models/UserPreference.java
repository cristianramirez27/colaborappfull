package com.coppel.rhconecta.dev.resources.db.models;

import com.coppel.rhconecta.dev.views.utils.AppConstants;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserPreference extends RealmObject {

    @PrimaryKey
    private String email;
    private RealmList<HomeMenuItem> menuItems;
    private byte[] image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<HomeMenuItem> getMenuItems() {
        RealmList<HomeMenuItem> tempList = new RealmList<>();
        for(HomeMenuItem menuItem: menuItems) {
          //  if(!menuItem.getTAG().equals(AppConstants.OPTION_BENEFITS)) {
                tempList.add(menuItem);
           // }
        }
        return tempList;
    }

    public void setMenuItems(RealmList<HomeMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
