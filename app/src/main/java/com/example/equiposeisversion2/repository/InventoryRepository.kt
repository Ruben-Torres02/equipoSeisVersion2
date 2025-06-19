package com.example.equiposeisversion2.repository

import android.util.Log
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val apiServiceRaza: ApiServiceRaza
) {


    private val mascotasRef = firestore.collection("mascotas")
    private val counterRef = firestore.collection("contador").document("mascotas_counter")


    suspend fun saveInvMascota(mascota: InventoryMascota) {
        withContext(Dispatchers.IO) {
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(counterRef)
                val current = snapshot.getLong("count") ?: 0
                val newCount = current + 1

                transaction.update(counterRef, "count", newCount)

                val nuevaMascota = mascota.copy(numberId = newCount)
                transaction.set(mascotasRef.document(), nuevaMascota)
            }.await()
        }
    }

     fun getListInv(callback: (List<InventoryMascota>) -> Unit) {
        mascotasRef
            .orderBy("numberId", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error al escuchar mascotas", error)
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull {
                    it.toObject(InventoryMascota::class.java)
                } ?: emptyList()

                callback(lista)
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


