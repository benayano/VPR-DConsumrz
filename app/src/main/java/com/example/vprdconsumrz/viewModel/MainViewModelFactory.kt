package com.example.vprdconsumrz.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails

class MainViewModelFactory (private val accountRepository:AccountRepository,private val userDetails: UserDetails) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(accountRepository,userDetails) as T
    }
}