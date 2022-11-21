package com.example.abasoftleslintestcode.productList

import android.graphics.Movie
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.abasoftleslintestcode.pojo.SearchProductMobile
import kotlinx.coroutines.*

class ProductListViewModel
    @ViewModelInject
    constructor(
        private val repository: ProductListRepository,
        @Assisted private val  savedStateHandle: SavedStateHandle
    ):ViewModel()
{


    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<SearchProductMobile>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                   // movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

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