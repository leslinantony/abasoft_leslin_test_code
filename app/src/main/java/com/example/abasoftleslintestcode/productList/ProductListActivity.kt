package com.example.abasoftleslintestcode.productList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abasoftleslintestcode.R
import com.example.abasoftleslintestcode.cart_activity.CartActivity
import com.example.abasoftleslintestcode.cart_activity.adapter.CartAdapter
import com.example.abasoftleslintestcode.databinding.ActivityProductListBinding
import com.example.abasoftleslintestcode.productList.adapter.Action
import com.example.abasoftleslintestcode.productList.adapter.ProductListAdapter
import com.example.abasoftleslintestcode.product_description.ProductDescriptionActivity
import com.example.abasoftleslintestcode.room.Cart
import kotlinx.android.synthetic.main.activity_product_description.*


class ProductListActivity : AppCompatActivity() {
    lateinit var viewModel: ProductListViewModel
    lateinit var binding: ActivityProductListBinding
    lateinit var adapter: ProductListAdapter
    var isSCrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrolledOutItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        binding.viewModel = viewModel

        setObservable(binding.rvProductList)

        viewModel.getAllProducts()
        viewModel.clearCart()


        binding.layoutCart.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        viewModel.getProductsFromCache().observe(this, Observer {


            adapter.setProductData(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getCartItemsFromCache().observe(this, Observer {
            if (it.size > 0) {
                binding.layoutCartCount.visibility = View.VISIBLE
                binding.tvCartItemCount.text = it.size.toString()
            } else {
                binding.layoutCartCount.visibility = View.GONE
            }
        })
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setObservable(rvProductList: RecyclerView) {

        val layoutManager = GridLayoutManager(this, 2)
        adapter = ProductListAdapter()
        rvProductList.layoutManager = layoutManager;
        rvProductList.adapter = adapter

        rvProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isSCrolling = true
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrolledOutItems = layoutManager.findFirstVisibleItemPosition()
                var temp = (currentItems + scrolledOutItems)
                if (isSCrolling && (currentItems + scrolledOutItems == totalItems)) {
                    viewModel.pageNUmber.set(viewModel.pageNUmber.get()?.plus(1) ?: 1)
                    viewModel.getAllProducts()
                }
            }
        })

        setObserver(adapter)

    }

    private fun setObserver(adapter: ProductListAdapter) {

        adapter.mAction.observe(this, Observer {
            when (it.action) {
                Action.CLICK_PRODUCT -> {

                    var intent = Intent(this, ProductDescriptionActivity::class.java)
                    intent.putExtra("societyId", it.productList?.SocietyproductID.toString())
                    intent.putExtra("productId", it.productList?.SocietyproductID.toString())
                    startActivity(
                        intent
                    )
                }
                Action.CLICK_ADD_TO_CART -> {

                    var cartItem = it.productList?.let { it1 ->
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
                    it.productList?.let { it1 ->
                        viewModel.updateCartCount(
                            it.count,
                            it1.SocietyproductID,
                            cartItem
                        )
                    }


                    Toast.makeText(this, "Added to cart successfully", Toast.LENGTH_SHORT).show()
                }

                Action.CLICK_ADD -> {

//                    var cartItem = it.productList?.let { it1 ->
//                        Cart(
//                            SocietyproductID = it1.SocietyproductID,
//                            Title = it1.Title,
//                            OriginalImage = it1.OriginalImage,
//                            ThumbImage = it1.ThumbImage,
//                            Finalprice = it1.Finalprice,
//                            Mrp = it1.Mrp,
//                            CartQuantity = it1.CartQuantity,
//                            TotalCount = it1.TotalCount,
//                            CartId = it1.CartId,
//                            CartCount = it1.CartCount,
//                            Unit = it1.Unit
//                        )
//                    }
//                    it.productList?.let { it1 -> viewModel.updateCartCount(it.count, it1.SocietyproductID, cartItem) }

                }

                Action.CLICK_REMOVE -> {
//                    var cartItem = it.productList?.let { it1 ->
//                        Cart(
//                            SocietyproductID = it1.SocietyproductID,
//                            Title = it1.Title,
//                            OriginalImage = it1.OriginalImage,
//                            ThumbImage = it1.ThumbImage,
//                            Finalprice = it1.Finalprice,
//                            Mrp = it1.Mrp,
//                            CartQuantity = it1.CartQuantity,
//                            TotalCount = it1.TotalCount,
//                            CartId = it1.CartId,
//                            CartCount = it1.CartCount,
//                            Unit = it1.Unit
//                        )
//                    }
//                    it.productList?.let { it1 -> viewModel.updateCartCount(
//                        it.count,
//                        it1.SocietyproductID,
//                        cartItem
//                    ) }
                }

            }
        })
    }
}