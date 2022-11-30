package com.example.abasoftleslintestcode.cart_activity

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.abasoftleslintestcode.room.Cart
import com.example.abasoftleslintestcode.room.ProductDao
import kotlinx.coroutines.flow.Flow

class CartRepository(var dao: ProductDao) {

    fun getCartItems(): Flow<List<Cart>> {
        return dao.getCartItems()
    }


    fun getCartItemsFromCache(): Flow<List<Cart>> {
        return dao.getCartItems()
    }

    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun deleteCartItem(cart: Cart?) {
        if (cart != null) {
            dao.deleteCartItem(cart.SocietyproductID)
        }

    }

    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun insertCartItem(cart: Cart?) {
        if (cart != null) {
            dao.insertIntoCart(cart)
        }

    }

    fun updateCartCount(cartCount: Int, productId: Int): Int {
        return dao.updateCartCount(cartCount, productId)
    }

}