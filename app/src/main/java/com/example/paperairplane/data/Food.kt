package com.example.paperairplane.data

import java.io.Serializable

data class Food(var uuid:String?=null,var content:String?=null,var password:String?=null,var picture:String?=null,var rate:Int?=0,var review:String?=null,
    var title:String?=null, var username:String?=null
): Serializable
