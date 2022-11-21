package com.example.abasoftleslintestcode.pojo

import com.google.gson.annotations.SerializedName


data class SearchProductMobile(

    @SerializedName("SocietyproductID")
    var SocietyproductID: Int  ,

    @SerializedName("Title")
    var Title: String  ,

    @SerializedName("OriginalImage")
    var OriginalImage: String  ,

    @SerializedName("ThumbImage")
    var ThumbImage: String  ,

    @SerializedName("Finalprice")
    var Finalprice: Int  ,

    @SerializedName("Mrp")
    var Mrp: Int  ,

    @SerializedName("CartQuantity")
    var CartQuantity: Int  ,

    @SerializedName("TotalCount")
    var TotalCount: Int  ,

    @SerializedName("CartId")
    var CartId: Int  ,

    @SerializedName("CartCount")
    var CartCount: Int  ,

    @SerializedName("Unit")
    var Unit: String 

)