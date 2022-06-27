package com.example.vprdconsumrz.viewModel

import com.example.vprdconsumrz.data.AccountData
import com.example.vprdconsumrz.data.CommentDataView
import com.example.vprdconsumrz.data.ErrorData
import com.example.vprdconsumrz.data.PostData
import com.example.vprdconsumrz.model.responce.*

class Convertor {
    fun responseToPostData(posts: ResponseData<PostsResponse>): List<PostData> {

       val listPostData = posts.data.posts.map {
            PostData(
                id = it.id,
                title = it.title,
                body = it.text ?: ""
            )
        }
        return listPostData
    }

    fun responseTeAccount(account: ResponseData<AccountDataResponse>): AccountData =
        AccountData(
            id = account.data.id,
            email = account.data.email,
            password = account.data.password
        )

    fun responseToCommentData(comments: ResponseData<CommentsResponse>): List<CommentDataView>? {
        val listCommentData = comments.data.comments?.map {
            CommentDataView(
                id= it.id,
            owner= it.owner ,// User.id (publisher)
             text= it.text,
             created_at= it.created_at, // (datetime)
             updated_at= it.updated_at, // (datetime)
             module=it.module, // posts
             module_id= it.module_id,// post.id
            )
        }
        return listCommentData
    }

    fun responseErrorToData(errors: List<ErrorDataResponse>): List<ErrorData>? {
        return errors.map {
            ErrorData(
                code = it.code,
                message = it.message
            )
        }
    }

    fun dataAccountToResponse(email: String, password: String): AccountData {
        return AccountData(id =10,email = email,password =password)
    }

}