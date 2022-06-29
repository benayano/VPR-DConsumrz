package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
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

private const val ID_POST = "idPost"
private const val TITLE_POST = "titlePost"
private const val POST_BODY = "bodyPost"

class EditPost : Fragment(R.layout.fragment_edit_post) {

    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails = UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }

    private val btnEdit: Button by lazy { requireView().findViewById(R.id.btnEditPost) }
    private val titleEditPost: TextInputEditText by lazy { requireView().findViewById(R.id.tittleEditPost) }
    private val bodyEditPost: TextInputEditText by lazy { requireView().findViewById(R.id.bodyEditPost) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postTitle?.let { title ->
            titleEditPost.text ?: title
        }
        postBody?.let { body ->
            bodyEditPost.text ?: body
        }

        btnEdit.setOnClickListener {
            postId?.let { postId ->
                mainViewModel.editPost(
                    id = postId,
                    title = titleEditPost.text.toString(),
                    body = bodyEditPost.text.toString()
                )
            }
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, MainUser())
            }
        }

    }

    private var postId: Int? = null
    private var postTitle: String? = null
    private var postBody: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getInt(ID_POST)
            postTitle = it.getString(TITLE_POST)
            postBody = it.getString(POST_BODY)
        }
    }

    companion object {
        @JvmStatic
        fun newInstanceEditPost(id: Int, postTitle: String, postBody: String) =
            EditPost().apply {
                arguments = Bundle().apply {
                    putString(ID_POST, postTitle)
                    putString(TITLE_POST, postBody)
                    putString(POST_BODY, postBody)
                }
            }
    }
}
