package com.coppel.rhconecta.dev.data.analytics.model.send_analytics_action

import androidx.annotation.Keep

@Keep
data class SendAnalyticsActionRequest(
        val num_empleado: Long,
        val clv_sistema: Int,
        val clv_acceso: Int,
) {

    /* */
    val clv_opcion = 2

}

@Keep
data class SendAnalyticsActionResponse(
    val data: Data? = null
) {

    /** */
    data class Data(
        val response: Response? = null
    )

    /** */
    data class Response(
        var errorCode: Int = 0,
        var Msg: String? = null,
    ) {

        /** */
        fun wasFailed(): Boolean {
            return errorCode != 0
        }

    }

}
