package com.example.vprdconsumrz.model.responce

import kotlinx.serialization.Serializable

@Serializable

data class ResponseData<T>(
    val data: T,
    val errors: List<ErrorDataResponse>? = null
) {

    //fun isSuccessful(): Boolean = this.isSuccessful()

     val isSuccessful: Boolean by lazy { this.isSuccessful }
}

@Serializable
data class ErrorDataResponse(
    val code: String,
    val message: String?
)