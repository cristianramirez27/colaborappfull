package com.coppel.rhconecta.dev.business.Enums;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.StringRes;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IEnumTab;


/**
 * @author flima on 22/05/2017.
 */
@Keep
public enum ElementsDepositTab implements IEnumTab {

    CORRIENTE(R.string.acount_title,NO_ICON),
    AHORRO(R.string.aditional_title,NO_ICON),
    TRABAJADOR(R.string.worker_title, NO_ICON);


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
