package com.example.abasoftleslintestcode.productList.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.abasoftleslintestcode.databinding.ActivityProductListBinding
import com.example.abasoftleslintestcode.databinding.LayoutRowProductBinding
import com.example.abasoftleslintestcode.room.ProductList

class ProductsRowViewModel(
    var productList: ProductList,
    var mAction: MutableLiveData<Action>,
    binding: LayoutRowProductBinding
) : BaseObservable() {

    var productName: String? = productList.Title
    var productPrice: String? = "Rs."+productList.Finalprice.toString()
    var itemCount: String? = "0"
    var count: Int? = 0

    init {
        Glide.with(binding.root).load(productList.ThumbImage).into(binding.imgProductImage);
    }
    fun clickAdd(view:View)
    {
        count= count?.plus(1)
        itemCount= count.toString()
    }

    fun clickRemove(view:View)
    {
        if (count!! >0) {
            count = count?.minus(1)
            itemCount= count.toString()
        }
    }

    fun clickProduct(view: View)
    {
        mAction.value= Action(Action.CLICK_PRODUCT,productList )
    }

    fun clickAddToCart(view: View)
    {
        mAction.value= Action(Action.CLICK_ADD_TO_CART)
    }



}