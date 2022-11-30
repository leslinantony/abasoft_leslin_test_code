package com.example.abasoftleslintestcode.product_description

import android.app.Application
import android.icu.text.CaseMap
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.product_description.pojo.GetProductDetails
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.Cart
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

class ProductDescriptionViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage = MutableLiveData<String>()
    var productDetails = MutableLiveData<GetProductDetails>()
    var productList = MutableLiveData<List<ProductList>>()
    var job: Job? = null

    public var productId = ObservableField("")
    public var societyId = ObservableField("")
    public var itemCount = ObservableField("")
    public var count: Int = 0
    var isItemAdded = false
    lateinit var prooduct: ProductList
    lateinit var cart: Cart
    val loading = MutableLiveData<Boolean>()
    private val repository = (application as MyApplication).productDescriptionRepository
    fun getProductDetails() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val jsonParams: MutableMap<String?, Any?> = HashMap()
            jsonParams["CustomerId"] = "sNhZrOJ/BHc="
            jsonParams["SocietyId"] = "1"//societyId.get()
            jsonParams["ProductID"] = productId.get()


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
                            productDetails.value = response.body()
                            loading.value = false
                        } else {
                            onError("Error : ${response.message()} ")
                        }
                    }
                }
            } catch (e: Exception) {

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

    fun clickAdd(view: View) {
        count++
        itemCount.set(count.toString())
    }

    fun clickRemove(view: View) {
        if (count!! > 0) {
            count--
            itemCount.set(count.toString())
        }
    }

    fun clickProduct(view: View) {

    }

    fun clickAddToCart(view: View) {

        if (count > 0) {
            if (!isItemAdded){
                cart=Cart(
                    SocietyproductID = prooduct.SocietyproductID,
                    Title = prooduct.Title,
                    OriginalImage = prooduct.OriginalImage,
                    ThumbImage = prooduct.ThumbImage,
                    Finalprice = prooduct.Finalprice,
                    Mrp = prooduct.Mrp,
                    CartQuantity = prooduct.CartQuantity,
                    TotalCount = prooduct.TotalCount,
                    CartId = prooduct.CartId,
                    CartCount = prooduct.CartCount,
                    Unit = prooduct.Unit
                )
            }
            productId.get()?.let { updateCartCount(count, it.toInt(), cart) }
            Toast.makeText(view.context, "${prooduct.Title} is added to cart", Toast.LENGTH_SHORT).show()
        }

    }


    fun updateCartCount(cartCount: Int, productId: Int, cartItem: Cart?) {
        var result = 0
        CoroutineScope(Dispatchers.IO).launch {
            if (repository != null) {
                result = repository.updateCartCount(cartCount, productId)
                if (result != 0) {
                    if (cartCount == 0) {
                        cartItem.let { repository.deleteCartItem(cartItem) }
                    } else {
                        cartItem.let { repository.insertCartItem(cartItem) }
                    }


                }

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

    fun isItemAddedToCart(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (repository != null) {
                isItemAdded = repository.isItemAddedToCart(productId)
                if (isItemAdded) {
                    var response = repository.getCartItem(productId)
                    response.collect{
                        cart= it
                        count= it.CartQuantity
                        itemCount.set(count.toString())
                    }

                } else {
                    var response = repository.getProductItem(productId)
                    response.collect{
                        prooduct= it
                    }
                }


            }

        }
    }


}