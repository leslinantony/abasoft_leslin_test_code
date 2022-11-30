package com.example.abasoftleslintestcode.cart_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abasoftleslintestcode.application.MyApplication
import com.example.abasoftleslintestcode.room.Cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    var cartList = MutableLiveData<List<Cart>>()
    private val repository = (application as MyApplication).cartRepository

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
                    if (cartCount==0){
                        cartItem.let {  repository.deleteCartItem(cartItem) }
                    }else{
                        cartItem.let {  repository.insertCartItem(cartItem) }
                    }


                }

            }
        }



    }
}