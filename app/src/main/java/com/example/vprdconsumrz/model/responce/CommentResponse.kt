package com.example.vprdconsumrz.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val id: Int,
    val owner: Int? ,// User.id (publisher)
    val title:String,
    val text: String?,
    val created_at: String?, // (datetime)
    val updated_at: String?, // (datetime)
    val module: String?, // posts
    val module_id: Int? ,// post.id

)
@Serializable

data class CommentsResponse(
    val comments: List<CommentResponse>?
)
