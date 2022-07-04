package com.example.vprdconsumrz.data

data class AccountData(
    val id: Int,
    val email: String,
    val password:String? =null
){
    override fun toString(): String {
        return "($id,$email,$password)"
    }
}
