package com.example.equiposeisversion2.webservice


import com.example.equiposeisversion2.model.ImagenResponse
import com.example.equiposeisversion2.model.RazaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceRaza {

    @GET("breeds/list/all")
    suspend fun getBreeds(): RazaResponse

    @GET("breed/{breed}/images/random")
    suspend fun getRandomImage(@Path("breed") breed: String): ImagenResponse
}