package com.example.abasoftleslintestcode.productList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abasoftleslintestcode.R
import com.example.abasoftleslintestcode.databinding.ActivityProductListBinding
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.productList.adapter.ProductListAdapter
import com.example.abasoftleslintestcode.product_description.ProductDescriptionActivity


class ProductListActivity : AppCompatActivity() {
    lateinit var viewModel: ProductListViewModel
    lateinit var binding: ActivityProductListBinding
    lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_product_list)
        viewModel= ViewModelProvider(this)[ProductListViewModel::class.java]
        binding.viewModel= viewModel

        setObservable(binding.rvProductList)

        viewModel.getAllProducts()

        viewModel.getProductsFromCache().observe(this, Observer {


            adapter.setProductData(it)

        })

        viewModel.loading.observe(this, Observer {
            if(it){
            binding.progressBar.visibility= View.VISIBLE}else{
                binding.progressBar.visibility= View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setObservable(rvProductList: RecyclerView) {

        val layoutManager = GridLayoutManager(this, 2)
        adapter= ProductListAdapter()
        rvProductList.setLayoutManager(layoutManager);
        rvProductList.adapter=adapter

        setObserver(adapter)

    }

    private fun setObserver(adapter: ProductListAdapter) {

        adapter.mAction.observe(this, Observer {
            when(it.action)
            {
                Action.CLICK_PRODUCT->{

                    var intent= Intent(this, ProductDescriptionActivity::class.java)
                    intent.putExtra("societyId",it.productList?.SocietyproductID.toString())
                    intent.putExtra("productId",it.productList?.SocietyproductID.toString())
                    startActivity(
                        intent
                    )
                }
                Action.CLICK_ADD_TO_CART->{
                    Toast.makeText(this, "Added to cart successfully", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}