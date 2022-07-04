package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails
import com.example.vprdconsumrz.viewModel.MainViewModel
import com.example.vprdconsumrz.viewModel.MainViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class AddingNewPost : Fragment(R.layout.fragment_adding_new_post) {

    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails = UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }


    private val btnPublish: Button by lazy { requireView().findViewById(R.id.btnPublish) }
    private val titlePost: TextInputEditText by lazy { requireView().findViewById(R.id.tittleAddPost) }
    private val bodyPost: TextInputEditText by lazy { requireView().findViewById(R.id.bodyAddPost) }
    private val ivBackNewPost: ImageView by lazy { requireView().findViewById(R.id.ivBackNewPost) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Adding New Post🌟"


        btnPublish.setOnClickListener {

            if (titleOrBodyEmpty()){
                shortMsg("no value in Post")
            }else{
                mainViewModel.createPost(title = titlePost.text.toString(), body = bodyPost.text.toString())
                longMsg("${titlePost.text} \n ${bodyPost.text}")
                returnToMainUser()
            }

        }
        ivBackNewPost.setOnClickListener {
            returnToMainUser()
        }
    }
    private fun returnToMainUser(){
        parentFragmentManager.commit {
            replace(R.id.mainContainerFragment, MainUser())
        }
    }
    private fun titleOrBodyEmpty(): Boolean {
        val title = titlePost.text.toString()
        val body = bodyPost.text.toString()
        return title.isBlank() || title.isNullOrEmpty() || body.isBlank() || body.isNullOrEmpty()
    }

    private fun shortMsg(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

    private fun longMsg(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}