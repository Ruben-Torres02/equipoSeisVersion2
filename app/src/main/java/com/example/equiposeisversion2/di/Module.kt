package com.example.equiposeisversion2.di

import android.content.Context
import androidx.room.Room
import com.example.equiposeisversion2.data.InventoryBD
import com.example.equiposeisversion2.data.InventoryDao
import com.example.equiposeisversion2.utils.Constants
import com.example.equiposeisversion2.utils.Constants.BASE_URL
import com.example.equiposeisversion2.webservice.ApiServiceRaza
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideInventoryDB(@ApplicationContext context: Context):InventoryBD{
        return Room.databaseBuilder(
            context,
            InventoryBD::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiServiceRaza {
        return retrofit.create(ApiServiceRaza::class.java)
    }

    @Singleton
    @Provides
    fun provideDaoReto(inventoryBD: InventoryBD): InventoryDao {
        return inventoryBD.inventoryDao()
    }
}