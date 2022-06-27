package com.example.vprdconsumrz.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: Int,
    val owner: Int? = null, // User.id (publisher)
    val title: String,
    val text: String? = null,
    val created_at: String? = null,// (datetime)
    val updated_at: String? = null// (datetime)
)

@Serializable
data class PostsResponse(
    val posts:List<PostResponse>
)

