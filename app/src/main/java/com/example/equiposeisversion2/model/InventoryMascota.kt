package com.example.equiposeisversion2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class InventoryMascota (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameMascota: String,
    val raza: String,
    val namePropietario: String,
    val telefono: String,
    val sintomas: String

): Serializable