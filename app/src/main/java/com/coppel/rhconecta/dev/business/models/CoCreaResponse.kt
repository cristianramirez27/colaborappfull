package com.coppel.rhconecta.dev.business.models

class CoCreaResponse(val data: Data) : CoppelGeneralParameterResponse() {
    class Data(val response: Response)
    class Response(val authCode: String)
}
