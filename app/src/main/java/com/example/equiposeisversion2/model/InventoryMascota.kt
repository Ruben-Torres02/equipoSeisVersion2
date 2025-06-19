package com.example.equiposeisversion2.model


import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class InventoryMascota (

    @DocumentId
    var id: String? = null,
    var nameMascota: String? = null,
    var raza: String? = null,
    var namePropietario: String? = null,
    var telefono: String? = null,
    var sintomas: String? = null,
    var numberId: Long? = null

): Serializable {
    constructor() : this(null, null, null, null, null, null, null)
}