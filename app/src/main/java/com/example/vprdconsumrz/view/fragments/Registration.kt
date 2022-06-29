package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.data.AccountData
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails
import com.example.vprdconsumrz.view.fragments.MainUser.Companion.newInstanceMainUser
import com.example.vprdconsumrz.viewModel.MainViewModel
import com.example.vprdconsumrz.viewModel.MainViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class Registration : Fragment(R.layout.fragment_registration) {

    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails = UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }


    private val tvHaveAccount: TextView by lazy { requireView().findViewById(R.id.tvHaveAccount) }
    private val btnRegistration: Button by lazy { requireView().findViewById(R.id.btnRegistration) }
    private val password2: TextInputEditText by lazy { requireView().findViewById(R.id.password2FieldSigning) }
    private val password: TextInputEditText by lazy { requireView().findViewById(R.id.passwordFieldSigning) }
    private val email: TextInputEditText by lazy { requireView().findViewById(R.id.emailFieldRegistration) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnRegistration.setOnClickListener {
            parentFragmentManager.commit {
                when{
                    emailAndPasswordEmpty()->shortMsg("please enter valid password")
                    password.text.toString() != password2.text.toString()->shortMsg("please enter valid password")
                    else -> {
                        val email = email.text.toString()
                        val password= password.text.toString()
                        //for testing
                        mainViewModel.registration(email,password)
                       mainViewModel.getAccount().observe(viewLifecycleOwner){
                           mainViewModel.saveDetails(it)
                       }
                        replace(
                            R.id.mainContainerFragment,
                            newInstanceMainUser(
                                // for test
                                id =10,
                                email = email,
                                password = password
                            )
                        )
                    }
                }
            }
        }
        tvHaveAccount.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, Login())
            }
        }

    }

    //-------------------------------Auxiliary function -------------

    private fun emailAndPasswordEmpty(): Boolean {
        val email = email.text.toString()
        val password = password.text.toString()
        val password2 = password2.text.toString()

        return email.isBlank() || email.isNullOrEmpty() || password.isBlank() ||
                password.isNullOrEmpty() || password2.isNullOrEmpty() || password2.isBlank()

    }

    private fun shortMsg(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}