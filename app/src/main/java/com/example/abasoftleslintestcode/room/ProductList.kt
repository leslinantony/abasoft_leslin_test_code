package com.example.abasoftleslintestcode.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "products")
data class ProductList(
   @PrimaryKey(autoGenerate = false)
   @ColumnInfo(name = "SocietyproductID")
   var SocietyproductID: Int,

   @ColumnInfo(name ="Title")
   var Title: String? ,

   @ColumnInfo(name ="OriginalImage")
   var OriginalImage: String ,

   @ColumnInfo(name ="ThumbImage")
   var ThumbImage: String ,

   @ColumnInfo(name ="Finalprice")
   var Finalprice: Int ,

   @ColumnInfo(name ="Mrp")
   var Mrp: Int ,

   @ColumnInfo(name ="CartQuantity")
   var CartQuantity: Int ,

   @ColumnInfo(name ="TotalCount")
   var TotalCount: Int ,

   @ColumnInfo(name ="CartId")
   var CartId: Int ,

   @ColumnInfo(name ="CartCount")
   var CartCount: Int ,

   @ColumnInfo(name ="Unit")
   var Unit: String
   
   
   
){}