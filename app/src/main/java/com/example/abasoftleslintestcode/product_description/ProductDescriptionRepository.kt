package com.example.abasoftleslintestcode.product_description

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.Cart
import com.example.abasoftleslintestcode.room.ProductDao
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class ProductDescriptionRepository(private val dao: ProductDao)

{
    suspend fun getProductDetails(api: ApiInterface?, body: RequestBody) = api?.getProductDetails(body)


    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun insertCartItem(cart: Cart?) {
        if (cart != null) {
            dao.insertIntoCart(cart)
        }

    }

    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun deleteCartItem(cart: Cart?) {
        if (cart != null) {
            dao.deleteCartItem(cart.SocietyproductID)
        }

    }

    fun updateCartCount(cartCount: Int, productId: Int): Int {
        return dao.updateCartCount(cartCount, productId)
    }


    fun getAllProductsFromCache(): Flow<List<ProductList>> {
        return dao.getProducts()
    }

    fun isItemAddedToCart( productId: Int): Boolean {
        return dao.isItemAdded( productId )
    }

    fun getCartItem( productId: Int): Flow<Cart> {
        return dao.getCartItem( productId )
    }

    fun getProductItem( productId: Int): Flow<ProductList> {
        return dao.getProduct( productId )
    }

}