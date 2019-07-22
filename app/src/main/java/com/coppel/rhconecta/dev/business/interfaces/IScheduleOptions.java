package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.utils.Command;

public interface IScheduleOptions {

    void showEliminatedOption(boolean show,String name);
    void setActionEliminatedOption(Command action);
}
