package com.example.equiposeisversion2.webservice

import com.example.equiposeisversion2.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCliente {
     val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Tu base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiServiceRaza: ApiServiceRaza = retrofit.create(ApiServiceRaza::class.java)
}