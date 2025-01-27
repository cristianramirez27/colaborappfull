package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.utils.Command;

public interface IScheduleOptions {

    void showTitle(boolean show);
    void  changeIconToolbar(int icon);
    void showEliminatedOption(boolean show,String name);
    void showAuthorizeOption(boolean show);
    void setActionEliminatedOption(Command action);
    void setActionAuthorizeOption(Command action);
}
