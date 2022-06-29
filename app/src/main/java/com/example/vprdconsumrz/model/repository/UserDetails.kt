package com.example.vprdconsumrz.model.repository

import android.content.SharedPreferences
import com.example.vprdconsumrz.data.AccountData

class UserDetails(private val sharedPreferences: SharedPreferences) {

    private val editor = sharedPreferences.edit()

    companion object {
        private const val ID = "id"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }

    fun convertAndSaveAccountData(accountData: AccountData) {
        editor.apply {
            putInt(ID, accountData.id)
            putString(EMAIL, accountData.email)
            putString(PASSWORD, accountData.password)
            apply()
        }
    }

    fun loadUser() = AccountData(
        id = sharedPreferences.getInt(ID, -1),
        email = sharedPreferences.getString(EMAIL, "")!!,
        password = sharedPreferences.getString(PASSWORD, "")
    )


}
