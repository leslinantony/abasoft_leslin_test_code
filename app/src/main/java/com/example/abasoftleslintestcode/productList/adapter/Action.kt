package com.example.abasoftleslintestcode.productList.adapter

import com.example.abasoftleslintestcode.room.Cart
import com.example.abasoftleslintestcode.room.ProductList

class Action {

    companion object{

        public val DEFAULT:Int=-1
        public val CLICK_ADD:Int=1
        public val CLICK_REMOVE:Int=2
        public val CLICK_PRODUCT:Int=3
        public val CLICK_ADD_TO_CART:Int=4

    }
    var action:Int = 0
    var error:String?= null
    var productList: ProductList?= null
    var cart: Cart?= null
    var count: Int=0

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, productList: ProductList?) {
        this.action = action
        this.productList = productList
    }

    constructor(action: Int, productList: ProductList?, count: Int) {
        this.action = action
        this.productList = productList
        this.count = count
    }

    constructor(action: Int, cart: Cart?, count: Int) {
        this.action = action
        this.cart = cart
        this.count = count
    }


}