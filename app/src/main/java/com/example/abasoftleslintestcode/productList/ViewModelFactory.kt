package com.example.abasoftleslintestcode.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//class MyViewModelFactory constructor(private val repository: ProductListRepository): ViewModelProvider.Factory {
//
//    override fu <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(ProductListViewModel::class.java!!)) {
//            ProductListViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}