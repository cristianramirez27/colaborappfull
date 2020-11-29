package com.coppel.rhconecta.dev.business.interfaces;

import android.content.Context;

import androidx.annotation.Keep;

/**
 * @author flima on 22/05/2017.
 */
@Keep
public interface IEnumTab {

    int NO_ICON = -10;

    String getName(Context context);

    int getIconRes();
}
