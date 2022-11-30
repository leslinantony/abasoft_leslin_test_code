package com.example.abasoftleslintestcode.product_description

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.abasoftleslintestcode.R
import com.example.abasoftleslintestcode.databinding.ActivityProductDescriptionBinding
import kotlinx.android.synthetic.main.activity_product_description.view.*

class ProductDescriptionActivity : AppCompatActivity() {

    lateinit var viewModel: ProductDescriptionViewModel
    lateinit var binding: ActivityProductDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_product_description)
        viewModel= ViewModelProvider(this)[ProductDescriptionViewModel::class.java]
        binding.viewModel= viewModel

        var intent= intent
        viewModel.productId.set(intent.getStringExtra("productId"))
       // viewModel.societyId.set(intent.getStringExtra("productId"))

        viewModel.getProductDetails()
        intent.getStringExtra("productId")?.let { viewModel.isItemAddedToCart(it.toInt()) }

        viewModel.getProductsFromCache().observe(this, Observer {
            for (product in it)
            {
                if (product.SocietyproductID == (viewModel.productId.get()?.toInt() ?: 0))
                {
                    viewModel.itemCount.set(product.CartQuantity.toString())
                    viewModel.count= product.CartQuantity
                }
            }

        })
        viewModel.productDetails.observe(this, Observer {

            binding.tvDescription.text= it.ProductDetails.productInfoDetails[0].Description
            binding.tvProductName.text= it.ProductDetails.productInfoDetails[0].Title
            binding.tvPageName.text= it.ProductDetails.productInfoDetails[0].Title
            binding.tvPrice.text= (it.ProductDetails.productInfoDetails[0].Finalprice).toString()
            Glide.with(binding.root).load(it.ProductDetails.productImageDetails[0].OriginalImage) .into(binding.imageView4);

        })
        viewModel.loading.observe(this, Observer {
            if(it){
                binding.progressBar2.visibility= View.VISIBLE}else{
                binding.progressBar2.visibility= View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


    }


