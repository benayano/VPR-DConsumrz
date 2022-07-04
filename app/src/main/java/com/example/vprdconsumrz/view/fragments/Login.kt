package com.example.vprdconsumrz.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails
import com.example.vprdconsumrz.view.fragments.MainUser.Companion.newInstanceMainUser
import com.example.vprdconsumrz.viewModel.MainViewModel
import com.example.vprdconsumrz.viewModel.MainViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class Login : Fragment(R.layout.fragment_login) {

    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails = UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }
    private val btnLogin: Button by lazy { requireView().findViewById(R.id.btnLogin) }
    private val tvForgot: TextView by lazy { requireView().findViewById(R.id.tvForgot) }
    private val tvRegister: TextView by lazy { requireView().findViewById(R.id.tvRegister) }

    private val emailField: TextInputEditText by lazy { requireView().findViewById(R.id.emailField) }
    private val passwordField: TextInputEditText by lazy { requireView().findViewById(R.id.passwordField) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Login🌟"

        btnLogin.setOnClickListener {
            if (emailAndPasswordEmpty()) {
                shortMsg("Pleas fill out the form in Full")
            } else {
                mainViewModel.signingInPost(
                    emailField.text.toString(),
                    passwordField.text.toString()
                )
                mainViewModel.getAccount().observe(viewLifecycleOwner) {
                    mainViewModel.saveDetails(it)
                    parentFragmentManager.commit {
                        replace(
                            R.id.mainContainerFragment,
                            newInstanceMainUser(it.id, it.email, it.password)
                        )
                    }
                }
            }
        }
        tvRegister.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, Registration())
            }
        }
        tvForgot.setOnClickListener {
            if (emailField.text.isNullOrEmpty() || emailField.text.isNullOrBlank()){
                shortMsg("pleas enter email address")
            }else{
                mainViewModel.forgotPassword(email = emailField.text.toString())
                shortMsg("we are currently sending you an email to the email you entered")
            }

        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        mainViewModel.getUserDetails().observe(viewLifecycleOwner){
            if (it.id != -1){
                parentFragmentManager.commit {
                    replace(R.id.mainContainerFragment,
                        newInstanceMainUser(it.id,it.email,it.password))
                }
            }
        }
    }

    private fun emailAndPasswordEmpty(): Boolean {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        return email.isBlank() || email.isNullOrEmpty() || password.isBlank() || password.isNullOrEmpty()
    }

    private fun shortMsg(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

}