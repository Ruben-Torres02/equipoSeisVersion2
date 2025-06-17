package com.example.equiposeisversion2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.equiposeisversion2.model.InventoryMascota


@Database(entities = [InventoryMascota::class], version = 1, exportSchema = false )
abstract class InventoryBD : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}