package com.example.abasoftleslintestcode.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductList::class], version = 1, exportSchema = false)
abstract class ProductDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao


    companion object{
        val DATABASE_NAME: String = "product_db"
    }
}