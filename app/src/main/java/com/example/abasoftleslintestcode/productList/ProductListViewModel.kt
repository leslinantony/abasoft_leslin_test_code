package com.example.abasoftleslintestcode.productList

import android.app.Application
import androidx.lifecycle.*
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.*
import java.lang.Exception


class ProductListViewModel(application: Application) :AndroidViewModel(application)
{
    val errorMessage = MutableLiveData<String>()
    var productList = MutableLiveData<List<ProductList>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    val repository= (application as MyApplication).productListRepository
    fun getAllProducts() {
        try {


            job = CoroutineScope(Dispatchers.IO).launch {
                val response = repository?.getAllProducts(ApiInterface.RetrofitClient.getApi())
                withContext(Dispatchers.Main) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            //                    movieList.postValue(response.body())
                            repository?.insert(response.body())
                            loading.value = false
                        } else {
                            onError("Error : ${response.message()} ")
                        }
                    }
                }
            }
        }catch (e:Exception){

        }
    }

    fun getProductsFromCache(): LiveData<List<ProductList>> {
        viewModelScope.launch {
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





