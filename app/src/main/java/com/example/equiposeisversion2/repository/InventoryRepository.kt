package com.example.equiposeisversion2.repository

import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val apiServiceRaza: ApiServiceRaza
) {


    private val mascotasRef
        get() = firestore.collection("mascotas")

    suspend fun saveInvMascota(mascota: InventoryMascota) {
        withContext(Dispatchers.IO) {
            mascotasRef.add(mascota).await()
        }
    }

    suspend fun getListInv(): MutableList<InventoryMascota> {
        return withContext(Dispatchers.IO) {
            val snapshot = mascotasRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(InventoryMascota::class.java)
            }.toMutableList()
        }
    }

    suspend fun deleteInventory(mascota: InventoryMascota) {
        withContext(Dispatchers.IO) {
            mascota.id?.let { id ->
                mascotasRef.document(id).delete().await()
            }
        }
    }

    suspend fun updateInventory(mascota: InventoryMascota) {
        withContext(Dispatchers.IO) {
            mascota.id?.let { id ->
                mascotasRef.document(id).set(mascota).await()
            }
        }
    }

    suspend fun getRaza(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServiceRaza.getBreeds()
                response.message
            } catch (e: Exception) {
                e.printStackTrace()
                emptyMap()
            }
        }
    }
}


