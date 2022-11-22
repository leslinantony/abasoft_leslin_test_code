package com.example.abasoftleslintestcode.productList

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.abasoftleslintestcode.pojo.SearchProductResponse
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductDao
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class ProductListRepository
constructor(
    private val dao: ProductDao,
//    private val apiInterface: ApiInterface
) {

    suspend fun getAllProducts(api: ApiInterface?, body: RequestBody) = api?.getProducts(body)


     fun getAllProductsFromCache(): Flow<List<ProductList>> {
        return  dao.getProducts()
    }

    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun insert(response: SearchProductResponse?)
    {
        if (response != null) {
            for (product in response.SearchProductMobile){

                var p= ProductList(
                    SocietyproductID = product.SocietyproductID,
                    Title = product.Title,
                    OriginalImage = product.OriginalImage,
                    ThumbImage = product.ThumbImage,
                    Finalprice = product.Finalprice,
                    Mrp = product.Mrp,
                    CartQuantity = product.CartQuantity,
                    TotalCount = product.TotalCount,
                    CartId = product.CartId,
                    CartCount = product.CartCount,
                    Unit = product.Unit
                )

                dao.insert(p)
            }
        }}

}