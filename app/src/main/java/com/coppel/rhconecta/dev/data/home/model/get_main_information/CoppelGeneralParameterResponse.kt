package com.coppel.rhconecta.dev.data.home.model.get_main_information

import java.io.Serializable

open class CoppelGeneralParameterResponse: Serializable {
    private var meta: Meta? = null

    fun getMeta(): Meta? {
        return meta
    }

    fun setMeta(meta: Meta?) {
        this.meta = meta
    }

    class Meta : Serializable {
        var status: String? = null
    }
}