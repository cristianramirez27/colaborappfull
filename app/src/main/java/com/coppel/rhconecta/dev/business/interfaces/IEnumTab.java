package com.coppel.rhconecta.dev.business.interfaces;

import android.content.Context;

/**
 * @author flima on 22/05/2017.
 */

public interface IEnumTab {

    int NO_ICON = -10;

    String getName(Context context);

    int getIconRes();
}
