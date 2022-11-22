package com.example.abasoftleslintestcode.retrofit

import com.example.abasoftleslintestcode.pojo.SearchProductResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

//    @GET("movielist.json")
//    suspend fun getProducts(): Response<List<Movie>>

    @POST("listproducts")
    suspend fun getProducts(@Body body: RequestBody?): Response<SearchProductResponse>

    class RetrofitClient {

        companion object {
            var retrofitService: ApiInterface? = null
            fun getInstance() : ApiInterface {
                if (retrofitService == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://mockup.aabasoft.info/SampleprojectAPI/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    retrofitService = retrofit.create(ApiInterface::class.java)
                }
                return retrofitService!!
            }

        }
    }
}