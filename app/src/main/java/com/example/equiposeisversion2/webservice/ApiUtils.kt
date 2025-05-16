package com.example.equiposeisversion2.webservice

object ApiUtils {
    fun getApiServiceRaza(): ApiServiceRaza {
        return RetrofitCliente.retrofit.create(ApiServiceRaza::class.java)
    }
}