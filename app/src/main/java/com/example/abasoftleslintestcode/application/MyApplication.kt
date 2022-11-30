package com.example.abasoftleslintestcode.application

import android.app.Application
import com.example.abasoftleslintestcode.cart_activity.CartRepository
import com.example.abasoftleslintestcode.productList.ProductListRepository
import com.example.abasoftleslintestcode.product_description.ProductDescriptionRepository
import com.example.abasoftleslintestcode.room.ProductDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ProductDataBase.getDataBase(this, applicationScope) }
    val productListRepository by lazy { ProductListRepository(database.productDao(),)  }
    val productDescriptionRepository by lazy { ProductDescriptionRepository(database.productDao())  }
    val cartRepository by lazy { CartRepository(database.productDao())  }

}