package com.example.abasoftleslintestcode.cart_activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.abasoftleslintestcode.R
import com.example.abasoftleslintestcode.databinding.LayoutRowCartItemsBinding
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.room.Cart
import java.util.*

class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var cartList: List<Cart> = Collections.emptyList()
    var mAction= MutableLiveData<Action>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutRowCartItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_row_cart_items,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBindingData(cartList[position], mAction)
    }

    fun setProductData(cart: List<Cart>)
    {
        this.cartList= emptyList<Cart>()
        this.cartList=cart
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return  cartList.size
    }
    class ViewHolder(val binding: LayoutRowCartItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBindingData(cart: Cart, mAction: MutableLiveData<Action>) {

            binding.apply {
                this.viewModel= CartRowViewModel(cart, mAction, this)
            }

        }


    }


}