package com.example.abasoftleslintestcode.hilt

import com.example.abasoftleslintestcode.productList.ProductListRepository
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ProductRepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        dao: ProductDao,
        apiInterface: ApiInterface
    ): ProductListRepository{
        return ProductListRepository(dao, apiInterface)
    }

}