package com.example.vprdconsumrz.data

data class PostData(
    val id:Int ,
    val title:String,
    val body:String,
    val owner: Int? = null, // User.id (publisher)
    val created_at: String? = null,// (datetime)
    val updated_at: String? = null// (datetime)
)
