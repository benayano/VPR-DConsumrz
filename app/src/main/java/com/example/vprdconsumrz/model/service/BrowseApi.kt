package com.example.vprdconsumrz.model.service

import com.example.vprdconsumrz.model.responce.*
import retrofit2.http.*

interface BrowseApi {

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val TEXT = "text"
        private const val TITLE = "title"
        private const val ID = "id"
    }


    @DELETE("posts/{id}/comments")
    suspend fun deleteComment(
        @Query(ID) id: Int
    ): ResponseData<CommentResponse?>

    @PUT("posts/{id}/comments")
    suspend fun editComment(
        @Query(ID) id: Int,
        @Query(TEXT) text: String
    ): ResponseData<CommentResponse?>


    @POST("posts/{id}/comments")
    suspend fun postComment(
        @Query(TEXT) text: String
    ): ResponseData<CommentResponse?>


    //get
    @GET("posts/{id}/comments")
    suspend fun getComments(): ResponseData<CommentsResponse>

    //------------------------comments-----------------------------------------
    @DELETE("posts")
    suspend fun deletePost(
        @Query(ID) id: Int,
    ): ResponseData<PostsResponse?>

    @PUT("posts")
    suspend fun editPost(
        @Query(ID) id: Int,
        @Query(TITLE) title : String,
        @Query(TEXT) text: String
    ): ResponseData<PostsResponse?>


    @POST("posts")
    suspend fun postPost(
        @Query(TITLE) title : String,
        @Query(TEXT) text: String
    ): ResponseData<PostsResponse?>


    //get
    @GET("posts")
    suspend fun getPosts():ResponseData<PostsResponse>

//-------------------------------------Account----------------------------------------
    //Lost password

    @PUT("account/lostpassword")
    suspend fun forgotPassword(
        @Query(EMAIL) email: String
    ): ResponseData<AccountResponse?>

    //Registration

    @PUT("account")
    suspend fun registrationPut(
        @Query(PASSWORD) password: String
    ): ResponseData<AccountResponse?>

    @POST("account")
    suspend fun registration(
        @Query(EMAIL) email: String,
        @Query(PASSWORD) password: String
    ): ResponseData<AccountResponse?>

    @GET("account")
    suspend fun getAccount(): ResponseData<AccountResponse>

    //Signin

    @POST("account/signin")
    suspend fun signingInPost(
        @Query(EMAIL) email: String,
        @Query(PASSWORD) password: String
    ): ResponseData<AccountResponse?>

}