package com.example.vprdconsumrz.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vprdconsumrz.data.AccountData
import com.example.vprdconsumrz.data.CommentDataView
import com.example.vprdconsumrz.data.ErrorData
import com.example.vprdconsumrz.data.PostData
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails
import com.example.vprdconsumrz.model.responce.CommentResponse
import com.example.vprdconsumrz.model.responce.PostsResponse
import com.example.vprdconsumrz.model.responce.ResponseData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val accountRepository: AccountRepository ,private val userDetails: UserDetails) : ViewModel() {

    private val tag = "mainViewModel"

    private val userLivData :MutableLiveData<AccountData> by lazy {
        MutableLiveData()
    }

    private val postsData: MutableLiveData<List<PostData>> by lazy {
        MutableLiveData()
    }
    private val commentsData: MutableLiveData<List<CommentDataView>> by lazy {
        MutableLiveData()
    }
    private val accountLiveData: MutableLiveData<AccountData> by lazy {
        MutableLiveData()
    }

    private var accountErrorData: List<ErrorData>? = null
    private val convertor = Convertor()


    //------------------------comments-----------------------------------------
    fun getComment() = commentsData
    private fun loadComment() = viewModelScope.launch {
        commentsData.postValue(convertor.responseToCommentData(accountRepository.getComments()))
    }

    fun deleteComments(id: Int) = viewModelScope.launch {
        val response = try {
            accountRepository.deleteComments(id)
        } catch (e: IOException) {
            Log.e(tag, "IOException,Add Post-- you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(tag, "HttpException,Add Post-- unexpected response")
            return@launch
        }

        if (responseCommentIsWell(response)){
            loadComment()
        }
    }

    fun editComment(id: Int, text: String) = viewModelScope.launch {
        val response = try {
            accountRepository.editComments(id = id, text = text)
        } catch (e: IOException) {
            Log.e(tag, "IOException,Add Post-- you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(tag, "HttpException,Add Post-- unexpected response")
            return@launch
        }

        if (responseCommentIsWell(response)){
            loadComment()
        }
    }

    fun postComments(text: String) = viewModelScope.launch {
        val response = try {
            accountRepository.postComments(text = text)
        } catch (e: IOException) {
            Log.e(tag, "IOException,Add Post-- you might not have internet connection")
            return@launch
        } catch (e: HttpException) {
            Log.e(tag, "HttpException,Add Post-- unexpected response")
            return@launch
        }

        if (responseCommentIsWell(response)){
            loadComment()
        }
    }


    //-------------------------------------posts----------------------------------------

    fun getAllPosts() = postsData
    private fun loadPosts() {
        viewModelScope.launch {
            postsData.postValue(convertor.responseToPostData(accountRepository.getPosts()))
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            val response = try {
                accountRepository.deletePost(id)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Add Post-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Add Post-- unexpected response")
                return@launch
            }
            if (responseIsWell(response)){
                loadPosts()
            }

        }
    }

    fun editPost(id: Int, title: String, body: String) {
        viewModelScope.launch {
            val response = try {
                accountRepository.editPost(id = id, title = title, text = body)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Add Post-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Add Post-- unexpected response")
                return@launch
            }
            if (responseIsWell(response)){
                loadPosts()
            }
            if(responseIsWell(response)){
                loadPosts()
            }
        }
    }


    fun createPost(title: String, body: String) {
        viewModelScope.launch {

           val response = try {
                accountRepository.postPost(title = title, text = body)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Add Post-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Add Post-- unexpected response")
                return@launch
            }

            if (responseIsWell(response)){
                loadPosts()
            }
        }
    }

    private fun responseIsWell(response: ResponseData<PostsResponse?>) =
        response.isSuccessful && response.errors == null

    private fun responseCommentIsWell(response: ResponseData<CommentResponse?>) =
        response.isSuccessful && response.errors == null
//-------------------------------------Account----------------------------------------

    fun getAccountErrorData1() = accountErrorData

    fun getAccount() = accountLiveData

    private fun loadAccount(accountData: AccountData) {
        viewModelScope.launch {
            accountLiveData.postValue(accountData)
            //accountLiveData.postValue(convertor.responseTeAccount(accountRepository.getAccount()))
        }
    }


    fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                accountRepository.forgotPassword(email = email)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Add Post-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Add Post-- unexpected response")
                return@launch
            }

        }
    }

    //Registration

    fun registrationPut(password: String) {
        viewModelScope.launch {
            try {
                accountRepository.registrationPut(password = password)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Add Post-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Add Post-- unexpected response")
                return@launch
            }

        }
    }

    fun registration(email: String, password: String) {
        viewModelScope.launch {
            // loadAccount()
            val response = try {
                accountRepository.registration(email = email, password = password)
            } catch (e: IOException) {
                Log.e(tag, "IOException,Registration-- you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException,Registration-- unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.errors == null) {
                loadAccount(convertor.dataAccountToResponse(email, password))
                getAccount()
            }


        }
    }

    //Signin

    fun signingInPost(email: String, password: String) =
        viewModelScope.launch {
            val response = try {
                accountRepository.signingInPost(email, password)
            } catch (e: IOException) {
                Log.e(tag, "IOException, you might not have internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(tag, "HttpException, unexpected response")
                return@launch
            }
            if (response.isSuccessful && response.errors == null) {
                loadAccount(convertor.dataAccountToResponse(email, password))
                getAccount()
            }
        }
//---------------------------------------------------------AccountData----------------------------
    fun getUserDetails()= userLivData

    private fun loadAccountData() = viewModelScope.launch {
        userLivData.postValue(userDetails.loadUser())
    }
    fun saveDetails(accountData: AccountData) = viewModelScope.launch {
        userDetails.convertAndSaveAccountData(accountData)
        loadAccountData()
    }
}