package com.example.abasoftleslintestcode.retrofit

import com.example.abasoftleslintestcode.pojo.SearchProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("listproducts")
    suspend fun getProducts()   : Response<SearchProductResponse>
}