package com.example.equiposeisversion2.model

import com.google.gson.annotations.SerializedName

data class RazaResponse (
    @SerializedName("message")
    val message: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)
