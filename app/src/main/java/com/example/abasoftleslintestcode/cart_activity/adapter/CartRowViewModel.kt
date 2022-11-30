package com.example.abasoftleslintestcode.cart_activity.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.abasoftleslintestcode.databinding.LayoutRowCartItemsBinding
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.room.Cart

class CartRowViewModel(
    var cart: Cart,
    var mAction: MutableLiveData<Action>,
    var binding: LayoutRowCartItemsBinding
): BaseObservable() {


    var productName: String? = cart.Title
    var productPrice: String? = "Rs."+cart.Finalprice.toString()
    var itemCount: String? = cart.CartQuantity.toString()
    var count: Int = cart.CartQuantity

    init {
        Glide.with(binding.root).load(cart.ThumbImage).into(binding.imgProductImage);
    }
    fun clickAdd(view: View)
    {
        count += 1
        itemCount= count.toString()
        mAction.value= Action(Action.CLICK_ADD,cart , count)
        binding.tvItemCount.text= itemCount
    }

    fun clickRemove(view: View)
    {
        if (count >0) {
            count -= 1
            itemCount= count.toString()
            mAction.value= Action(Action.CLICK_REMOVE,cart , count)
            binding.tvItemCount.text= itemCount
        }


    }


}