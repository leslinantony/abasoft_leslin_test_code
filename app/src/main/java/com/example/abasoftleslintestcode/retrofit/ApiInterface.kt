package com.example.abasoftleslintestcode.retrofit

import com.example.abasoftleslintestcode.pojo.SearchProductResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("listproducts")
    suspend fun getProducts(): Response<SearchProductResponse>

    class RetrofitClient {



        companion object {
            private var instance: ApiInterface? = null
            public fun getApi(): ApiInterface? {
                if (instance == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(interceptor)

                        .readTimeout(15, TimeUnit.SECONDS)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .build()

                    instance = Retrofit.Builder()

                        .baseUrl("http://mockup.aabasoft.info/SampleprojectAPI/")//local
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(client)
                        .build()
                        .create(ApiInterface::class.java)
                }
                return instance
            }
        }
    }
}