package com.example.abasoftleslintestcode.product_description

import com.example.abasoftleslintestcode.retrofit.ApiInterface
import okhttp3.RequestBody

class ProductDescriptionRepository {
    suspend fun getProductDetails(api: ApiInterface?, body: RequestBody) = api?.getProductDetails(body)
}