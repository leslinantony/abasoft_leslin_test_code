package com.example.abasoftleslintestcode.pojo

import com.google.gson.annotations.SerializedName
import java.util.*


data class SearchProductResponse (

  @SerializedName("SearchProductMobile" )
  var SearchProductMobile : ArrayList<SearchProductMobile>,

  @SerializedName("TotalCount"          )
  var TotalCount          : Int?

)