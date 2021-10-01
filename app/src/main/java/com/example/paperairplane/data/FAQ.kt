package com.example.paperairplane.data

import java.io.Serializable

data class FAQ(var content:String?=null,var title:String?=null,var isExpanded: Boolean = false):
    Serializable
