package com.example.abasoftleslintestcode.productList

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.example.abasoftleslintestcode.pojo.SearchProductResponse
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.Cart
import com.example.abasoftleslintestcode.room.ProductDao
import com.example.abasoftleslintestcode.room.ProductList
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class ProductListRepository
constructor(
    private val dao: ProductDao,
//    private val apiInterface: ApiInterface
) {

    suspend fun getAllProducts(api: ApiInterface?, body: RequestBody) = api?.getProducts(body)


    fun getAllProductsFromCache(): Flow<List<ProductList>> {
        return dao.getProducts()
    }

    fun getCartItemsFromCache(): Flow<List<Cart>> {
        return dao.getCartItems()
    }

    fun updateCartCount(cartCount: Int, productId: Int): Int {
        return dao.updateCartCount(cartCount, productId)
    }
   fun updateCart(cartCount: Int, productId: Int): Int {
        return dao.updateCart(cartCount, productId)
    }

    fun clearCart() {
        return dao.clearCart()
    }

    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun insert(response: SearchProductResponse?) {
        if (response != null) {
            for (product in response.SearchProductMobile) {

                var p = ProductList(
                    SocietyproductID = product.SocietyproductID,
                    Title = product.Title,
                    OriginalImage = product.OriginalImage,
                    ThumbImage = product.ThumbImage,
                    Finalprice = product.Finalprice,
                    Mrp = product.Mrp,
                    CartQuantity = product.CartQuantity,
                    TotalCount = product.TotalCount,
                    CartId = product.CartId,
                    CartCount = product.CartCount,
                    Unit = product.Unit
                )

                dao.insert(p)
            }
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


    @SuppressLint("SuspiciousIndentation")
    @SuppressWarnings
    @WorkerThread
    suspend fun deleteCartItem(cart: Cart?) {
        if (cart != null) {
            dao.deleteCartItem(cart.SocietyproductID)
        }

    }

}