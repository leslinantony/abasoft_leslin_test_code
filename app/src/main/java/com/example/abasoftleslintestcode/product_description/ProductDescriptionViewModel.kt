package com.example.abasoftleslintestcode.product_description

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.product_description.pojo.GetProductDetails
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

class ProductDescriptionViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage = MutableLiveData<String>()
    var productDetails = MutableLiveData<GetProductDetails>()
    var job: Job? = null

    public var productId= ObservableField("")
    public var societyId= ObservableField("")
    public var itemCount= ObservableField("0")
    public var count: Int=0
    val loading = MutableLiveData<Boolean>()
    val repository= (application as MyApplication).productDescriptionRepository
    fun getProductDetails() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val jsonParams: MutableMap<String?, Any?> = HashMap()
            jsonParams["CustomerId"] = "sNhZrOJ/BHc="
            jsonParams["SocietyId"] = "1"//societyId.get()
            jsonParams["ProductID"] = "1"//productId.get()



            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(jsonParams).toString()
            )

            try {


                val response =
                    repository.getProductDetails(ApiInterface.RetrofitClient.getInstance(), body)
                withContext(Dispatchers.Main) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            productDetails.value= response.body()
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
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun clickAdd(view: View)
    {
        count++
        itemCount.set(count.toString())
    }

    fun clickRemove(view: View)
    {
        if (count!! >0) {
            count --
            itemCount.set(count.toString())
        }
    }

    fun clickProduct(view: View)
    {

    }

    fun clickAddToCart(view: View)
    {

    }


}