package com.example.abasoftleslintestcode.productList

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.abasoftleslintestcode.R

class ProductListActivity : AppCompatActivity() {
    lateinit var viewModel: ProductListViewModel


    //val viewModel by viewModels<ProductListViewModel>()
    //private val viewModel: ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        viewModel= ViewModelProvider(this)[ProductListViewModel::class.java]

//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory(application)
//        ).get(ProductListViewModel::class.java)
        viewModel.getAllProducts()

        viewModel.getProductsFromCache().observe(this, Observer {

            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()

        })
    }
}