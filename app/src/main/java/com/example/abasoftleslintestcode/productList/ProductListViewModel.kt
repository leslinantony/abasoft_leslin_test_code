package com.example.abasoftleslintestcode.productList

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.Cart
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
    var cartList = MutableLiveData<List<Cart>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()
    var pageNUmber= ObservableField(1)

    val repository= (application as MyApplication).productListRepository


    fun getAllProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val jsonParams: MutableMap<String?, Any?> = HashMap()
            jsonParams["CustomerId"] = "sNhZrOJ/BHc="
            jsonParams["SocietyId"] = "1"
            jsonParams["PageNumber"] = pageNUmber.get()
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


    fun getCartItemsFromCache(): LiveData<List<Cart>> {
        CoroutineScope(Dispatchers.Main).launch {
            if (repository != null) {

                var response = repository.getCartItemsFromCache()
                 response.collect{
                    cartList.value= it
                }
            }
        }
        return cartList
    }


    fun updateCartCount(cartCount: Int, productId: Int, cartItem: Cart?)
    {
        var result=0
        CoroutineScope(Dispatchers.IO).launch {
            if (repository != null) {
                result=repository.updateCartCount(cartCount,productId)
                if (result!=0)
                {
//                    var resultcart=repository.updateCart(cartCount,productId)
//                   if (resultcart!=0)
//                   {
                       if (cartCount==0){
                           cartItem.let {  repository.deleteCartItem(cartItem) }
                       }else{
                           cartItem.let {  repository.insertCartItem(cartItem) }
                      // }
                   }


                }

            }
        }



    }

    fun clearCart()
    {
        var result=0
        CoroutineScope(Dispatchers.IO).launch {
            if (repository != null) {
                repository.clearCart()

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





