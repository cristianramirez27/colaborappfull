package com.coppel.rhconecta.dev.data.home.model.get_main_information

import java.io.Serializable

class BaseResponse<T> : Serializable, CoppelGeneralParameterResponse() {
    private var data: T? = null

    fun getData(): T? {
        return data
    }

    fun setData(data: T?) {
        this.data = data
    }

    class Data<T> : Serializable {
        lateinit var response: Array<Response<T>>
    }

    class Response<T>(val data: T)
}