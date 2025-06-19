package com.example.equiposeisversion2.model


import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class InventoryMascota (

    @DocumentId
    val id: String? = null,
    val nameMascota: String,
    val raza: String,
    val namePropietario: String,
    val telefono: String,
    val sintomas: String

): Serializable