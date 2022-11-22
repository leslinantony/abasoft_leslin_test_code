package com.example.abasoftleslintestcode.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (productList: ProductList) : Long

    @Query( "select * from products ")
     fun getProducts() : Flow<List<ProductList>>

    @Query("delete from products")
     fun deleteAll()
}