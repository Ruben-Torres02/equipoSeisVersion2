package com.example.equiposeisversion2.repository

import android.content.Context
import com.example.equiposeisversion2.data.InventoryBD
import com.example.equiposeisversion2.data.InventoryDao
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import com.example.equiposeisversion2.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventoryRepository(val context: Context) {
    private var inventoryDao: InventoryDao = InventoryBD.getDatabase(context).inventoryDao()
    private var apiServiceRaza: ApiServiceRaza = ApiUtils.getApiServiceRaza()
    suspend fun saveInvMascota(inventoryMascota: InventoryMascota) {
        withContext(Dispatchers.IO) {
            inventoryDao.saveInvMascota(inventoryMascota)
        }
    }
    suspend fun getListInv():MutableList<InventoryMascota>{
        return withContext(Dispatchers.IO){
            inventoryDao.getListInv()
        }
    }

    suspend fun contarMascotas(): Int {
        return withContext(Dispatchers.IO) {
            inventoryDao.countMascotas()
        }
    }

    suspend fun deleteInventory(inventoryMascota: InventoryMascota){
        withContext(Dispatchers.IO){
            inventoryDao.deleteInv(inventoryMascota)
        }
    }

    suspend fun updateInventory(inventoryMascota: InventoryMascota){
        withContext(Dispatchers.IO){
            inventoryDao.updateInv(inventoryMascota)
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

    suspend fun getRandomImage(breed: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServiceRaza.getRandomImage(breed)
                response.message
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }

}


