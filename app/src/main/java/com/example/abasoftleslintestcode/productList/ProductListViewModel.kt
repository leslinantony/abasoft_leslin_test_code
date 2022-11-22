package com.example.abasoftleslintestcode.productList

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception


class ProductListViewModel(application: Application) :AndroidViewModel(application)
{
    val errorMessage = MutableLiveData<String>()
    var productList = MutableLiveData<List<ProductList>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    val repository= (application as MyApplication).productListRepository


    fun getAllProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val jsonParams: MutableMap<String?, Any?> = HashMap()
            jsonParams["CustomerId"] = "sNhZrOJ/BHc="
            jsonParams["SocietyId"] = "1"
            jsonParams["PageNumber"] = "1"
            jsonParams["PageSize"] = "5"


            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(jsonParams).toString()
            )

            try {


                val response =
                    repository.getAllProducts(ApiInterface.RetrofitClient.getInstance(), body)
                withContext(Dispatchers.Main) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            repository?.insert(response.body())
                            //Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT).show()
                            loading.value = false
                        } else {
                            onError("Error : ${response.message()} ")
                        }
                    }
                }
            }catch (e: Exception)
            {

            }
        }

    }





    fun getProductsFromCache(): LiveData<List<ProductList>> {
        CoroutineScope(Dispatchers.Main).launch {
            if (repository != null) {

                var response = repository.getAllProductsFromCache()
                 response.collect{
                    productList.value= it
                }
            }
        }
        return productList
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}





