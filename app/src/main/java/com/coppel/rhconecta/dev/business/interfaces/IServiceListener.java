package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;

public interface IServiceListener<T> {

    void onResponse(ServicesResponse<T> response);

    void onError(ServicesError coppelServicesError);
}
