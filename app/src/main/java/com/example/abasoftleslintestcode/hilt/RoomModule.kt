package com.example.abasoftleslintestcode.hilt

import android.content.Context
import androidx.room.Room
import com.example.abasoftleslintestcode.room.ProductDao
import com.example.abasoftleslintestcode.room.ProductDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase( @ApplicationContext context: Context) : ProductDataBase
    {
        return  Room.databaseBuilder(
            context,
            ProductDataBase::class.java,
            ProductDataBase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: ProductDataBase): ProductDao
    {
        return  database.productDao()
    }

}