package com.example.vprdconsumrz.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val id: Int,
    val email: String,
    val password:String? = null
)
