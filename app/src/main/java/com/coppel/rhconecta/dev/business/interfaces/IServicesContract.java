package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;

public interface IServicesContract {
    interface View<T> {

        void showResponse(ServicesResponse<T> response);

        void showError(ServicesError coppelServicesError);

        void showProgress();

        void hideProgress();
    }
}
