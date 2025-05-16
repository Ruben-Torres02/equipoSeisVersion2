package com.example.equiposeisversion2.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.equiposeisversion2.model.InventoryMascota

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInvMascota(inventoryMascota: InventoryMascota)

    @Query("SELECT * FROM InventoryMascota")
    suspend fun getListInv(): MutableList<InventoryMascota>

    @Query("SELECT COUNT(*) FROM InventoryMascota")
    suspend fun countMascotas(): Int

    @Delete
    suspend fun deleteInv(inventoryMascota: InventoryMascota)

    @Update
    suspend fun updateInv(inventoryMascota: InventoryMascota)
}
