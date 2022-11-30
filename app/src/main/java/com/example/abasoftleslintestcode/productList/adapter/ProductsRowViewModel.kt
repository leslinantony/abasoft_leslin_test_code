package com.example.abasoftleslintestcode.productList.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.abasoftleslintestcode.databinding.LayoutRowProductBinding

import com.example.abasoftleslintestcode.room.ProductList

class ProductsRowViewModel(
    var productList: ProductList,
    var mAction: MutableLiveData<Action>,
    var binding: LayoutRowProductBinding
) : BaseObservable() {

    var productName: String? = productList.Title
    var productPrice: String? = "Rs."+productList.Finalprice.toString()
    var itemCount: String? = productList.CartQuantity.toString()
    var count: Int = productList.CartQuantity

    init {
        Glide.with(binding.root).load(productList.ThumbImage).into(binding.imgProductImage);
    }
    fun clickAdd(view:View)
    {
        count += 1
        itemCount= count.toString()
        mAction.value= count?.let { Action(Action.CLICK_ADD,productList , it) }
        binding.tvItemCount.text= itemCount
    }

    fun clickRemove(view:View)
    {
        if (count!! >0) {
            count = count-1
            itemCount= count.toString()
            mAction.value= Action(Action.CLICK_REMOVE,productList , count)
            binding.tvItemCount.text= itemCount
        }


    }

    fun clickProduct(view: View)
    {
        mAction.value= Action(Action.CLICK_PRODUCT,productList )
    }

    fun clickAddToCart(view: View)
    {
        mAction.value= Action(Action.CLICK_ADD_TO_CART,productList , count)
    }



}