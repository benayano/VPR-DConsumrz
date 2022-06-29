package com.example.vprdconsumrz.model.repository

import com.example.vprdconsumrz.model.RetrofitCreator
import com.example.vprdconsumrz.model.responce.AccountResponse
import com.example.vprdconsumrz.model.responce.CommentsResponse
import com.example.vprdconsumrz.model.responce.PostsResponse
import com.example.vprdconsumrz.model.responce.ResponseData
import com.example.vprdconsumrz.model.service.BrowseApi

object AccountRepository {

    private val browseApi: BrowseApi by lazy {
        RetrofitCreator.getRetrofit("https://api/v1.0/")
    }

    suspend fun deleteComments(
        id: Int,
    ) = browseApi.deleteComment(id)

    suspend fun editComments(
        id: Int,
        text: String
    ) = browseApi.editComment(id = id, text = text)


    suspend fun postComments(
        text: String
    ) = browseApi.postComment(text = text)


    //get
    suspend fun getComments(): ResponseData<CommentsResponse> = browseApi.getComments()

    //------------------------comments-----------------------------------------
    suspend fun deletePost(
        id: Int
    ) = browseApi.deletePost(id = id)

    suspend fun editPost(
        id: Int,
        title: String,
        text: String
    ) = browseApi.editPost(id = id, title = title, text = text)


    suspend fun postPost(title: String, text: String) =
        browseApi.postPost(title = title, text = text)


    //get
    suspend fun getPosts(): ResponseData<PostsResponse> = browseApi.getPosts()

//-------------------------------------Account----------------------------------------
    //Lost password

    suspend fun forgotPassword(email: String) = browseApi.forgotPassword(email)

    //Registration

    suspend fun registrationPut(password: String) =
        browseApi.registrationPut(password = password)

    suspend fun registration(
        email: String,
        password: String
    ) = browseApi.registration(email = email, password = password)

    suspend fun getAccount(): ResponseData<AccountResponse> = browseApi.getAccount()

    //Signin

    suspend fun signingInPost(
        email: String,
        password: String
    ) = browseApi.signingInPost(email = email, password = password)

}
