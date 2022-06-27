package com.example.vprdconsumrz.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vprdconsumrz.model.repository.AccountRepository

class MainViewModelFactory (private val accountRepository:AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(accountRepository) as T
    }
}