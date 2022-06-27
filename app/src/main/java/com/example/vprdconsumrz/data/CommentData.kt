package com.example.vprdconsumrz.data

data class CommentDataView(
    val id: Int,
    val owner: Int? ,// User.id (publisher)
    val text: String?,
    val created_at: String?, // (datetime)
    val updated_at: String?, // (datetime)
    val module: String?, // posts
    val module_id: Int? ,// post.id
)
