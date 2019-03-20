package com.coppel.rhconecta.dev.business.Enums;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IEnumTab;


/**
 * @author flima on 22/05/2017.
 */

public enum ElementsDepositTab implements IEnumTab {

    NEWS(R.string.acount_title,NO_ICON),
    CATEGORIES(R.string.aditional_title,NO_ICON),
    AROUNDME(R.string.worker_title, NO_ICON);


    @StringRes
    private int name;

    @DrawableRes
    private int icon;

    private ElementsDepositTab(@StringRes int name, @DrawableRes int icon) {
        this.name = name;
        this.icon = icon;
    }


    @Override
    public String getName(Context context) {
        if(name == 0)
            return  "";
        else
         return context.getString(name);
    }

    @Override
    @DrawableRes
    public int getIconRes(){
        return this.icon;
    }


}
