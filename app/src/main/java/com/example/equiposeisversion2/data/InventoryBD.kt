package com.example.equiposeisversion2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.equiposeisversion2.model.InventoryMascota
import com.example.equiposeisversion2.utils.Constants.DATABASE_NAME

@Database(entities = [InventoryMascota::class], version = 1, exportSchema = false )
abstract class InventoryBD : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryBD? = null

        fun getDatabase(context: Context): InventoryBD {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryBD::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}