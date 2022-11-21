package com.example.abasoftleslintestcode.productList

import androidx.room.ColumnInfo
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductDao
import com.example.abasoftleslintestcode.room.ProductList

class ProductListRepository
constructor(
    private val dao: ProductDao,
    private val apiInterface: ApiInterface
) {

    suspend fun getAllProducts() = apiInterface.getProducts()


}