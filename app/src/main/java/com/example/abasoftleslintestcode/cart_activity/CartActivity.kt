package com.example.abasoftleslintestcode.cart_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abasoftleslintestcode.R
import com.example.abasoftleslintestcode.cart_activity.adapter.CartAdapter
import com.example.abasoftleslintestcode.databinding.ActivityCartBinding
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.productList.adapter.ProductListAdapter
import com.example.abasoftleslintestcode.room.Cart
import kotlinx.android.synthetic.main.activity_product_description.*
import kotlinx.android.synthetic.main.activity_product_list.*

class CartActivity : AppCompatActivity() {
    lateinit var viewModel: CartViewModel
    lateinit var binding: ActivityCartBinding
    lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_cart)
        viewModel= ViewModelProvider(this)[CartViewModel::class.java]
        binding.viewModel= viewModel

        setObservable(binding.rvCart)


        viewModel.getCartItemsFromCache().observe(this, Observer {

            adapter.setProductData(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setObservable(rvCart: RecyclerView) {

        val layoutManager = LinearLayoutManager(this)
        adapter= CartAdapter()
        rvCart.layoutManager = layoutManager;
        rvCart.adapter=adapter



        setObserver(adapter)

    }

    private fun setObserver(adapter: CartAdapter) {

        adapter.mAction.observe(this, Observer {
            when(it.action)
            {


                Action.CLICK_ADD->{

                    var cartItem = it.cart?.let { it1 ->
                        Cart(
                            SocietyproductID = it1.SocietyproductID,
                            Title = it1.Title,
                            OriginalImage = it1.OriginalImage,
                            ThumbImage = it1.ThumbImage,
                            Finalprice = it1.Finalprice,
                            Mrp = it1.Mrp,
                            CartQuantity = it.count,
                            TotalCount = it1.TotalCount,
                            CartId = it1.CartId,
                            CartCount = it1.CartCount,
                            Unit = it1.Unit
                        )
                    }
                    it.cart?.let { it1 -> viewModel.updateCartCount(it.count, it1.SocietyproductID, cartItem) }

                }

                Action.CLICK_REMOVE->{
                    var cartItem = it.cart?.let { it1 ->
                        Cart(
                            SocietyproductID = it1.SocietyproductID,
                            Title = it1.Title,
                            OriginalImage = it1.OriginalImage,
                            ThumbImage = it1.ThumbImage,
                            Finalprice = it1.Finalprice,
                            Mrp = it1.Mrp,
                            CartQuantity = it.count,
                            TotalCount = it1.TotalCount,
                            CartId = it1.CartId,
                            CartCount = it1.CartCount,
                            Unit = it1.Unit
                        )
                    }
                    it.cart?.let { it1 -> viewModel.updateCartCount(
                        it.count,
                        it1.SocietyproductID,
                        cartItem
                    ) }
                }
            }
        })

    }
}