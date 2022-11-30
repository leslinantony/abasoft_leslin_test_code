package com.example.abasoftleslintestcode.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (productList: ProductList) : Long

    @Query( "select * from products ")
     fun getProducts() : Flow<List<ProductList>>

     @Query( "select * from cart ")
     fun getCartItems() : Flow<List<Cart>>

    @Query("delete from products")
     fun deleteAll()

     @Query("delete from cart")
     fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart (cart: Cart) : Long
//
//    @Query( "select * from cart ")
//     fun getCartItems() : Flow<List<Cart>>
//
    @Query( "select * from cart ")
     fun updateCart() : Flow<List<Cart>>

     @Query( "select * from products where SocietyproductID= :productId ")
     fun getProduct(productId: Int) : Flow<ProductList>

     @Query( "select * from cart where SocietyproductID= :productId ")
     fun getCartItem(productId: Int) : Flow<Cart>

    @Query("DELETE FROM cart WHERE SocietyproductID = :productId")
    fun deleteCartItem(productId: Int)

    @Query( "update products set CartQuantity= :cartCount where SocietyproductID= :productId   ")
    fun updateCartCount(cartCount:Int, productId: Int): Int

    @Query( "update cart set CartQuantity= :cartCount where SocietyproductID= :productId   ")
    fun updateCart(cartCount:Int, productId: Int): Int

    @Query("SELECT EXISTS (SELECT 1 FROM cart WHERE SocietyproductID = :productId)")
    fun isItemAdded(productId: Int): Boolean





}