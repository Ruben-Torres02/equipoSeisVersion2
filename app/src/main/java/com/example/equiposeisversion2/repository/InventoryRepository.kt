package com.example.equiposeisversion2.repository

import com.example.equiposeisversion2.data.InventoryDao
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val apiServiceRaza: ApiServiceRaza
) {

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
}


