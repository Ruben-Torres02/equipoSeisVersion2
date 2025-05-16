package com.example.equiposeisversion2.model

import com.google.gson.annotations.SerializedName

data class ImagenResponse(
    @SerializedName("message")
    val message: String,
    val status: String

)