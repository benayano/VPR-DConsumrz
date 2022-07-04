package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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

private const val ID = "id"
private const val OWNER = "owner"
private const val TEXT = "text"
private const val CREATED_AT = "created_at"
private const val UPDATED_AT = "updated_at"
private const val MODULE = "module"
private const val MODULE_ID = "module_id"


class EditComment : Fragment(R.layout.fragment_edit_comment) {
    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails =UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }

    private val btnEditComment: Button by lazy { requireView().findViewById(R.id.btnEditComment) }
    private val bodyEditComment: TextInputEditText by lazy { requireView().findViewById(R.id.bodyEditComment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bodyEditComment.text?.let { bodyEditComment->
            text?.let { textx ->
                bodyEditComment ?:  textx
            }

        }

        btnEditComment.setOnClickListener {
            if (bodyEditComment.text.isNullOrEmpty() && bodyEditComment.text.isNullOrBlank()) {
                Toast.makeText(requireContext(), "the comment is empty", Toast.LENGTH_SHORT).show()
                parentFragmentManager.commit {
                    replace(R.id.mainContainerFragment, MainUser())
                }
            } else {
                createComment()
            }
        }
    }
    
    private fun createComment(){
        val comment = bodyEditComment.text.toString()
        id?.let {
            mainViewModel.editComment(id = it,text = comment)
        }
        if ( id ==null){
            mainViewModel.postComments( text = comment)
        }
    }

    private var id: Int? = null
    private var owner: Int? = null// User.id (publisher)
    private var text: String? = null
    private var createdAt: String? = null // (datetime)
    private var updatedAt: String? = null // (datetime)
    private var module: String? = null// posts
    private var moduleId: Int? = null// post.id)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
            owner = it.getInt(OWNER)
            text = it.getString(TEXT)
            createdAt = it.getString(CREATED_AT)
            updatedAt = it.getString(UPDATED_AT)
            module = it.getString(MODULE)
            moduleId = it.getInt(MODULE_ID)
        }
    }

    companion object {

        @JvmStatic
        fun newInstanceEditComment(
            id: Int,
            owner: Int?,// User.id (publisher)
            text: String?,
            created_at: String?, // (datetime)
            updated_at: String?, // (datetime)
            module: String?, // posts
            module_id: Int?,// post.id)
        ) =
            EditComment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                    if (owner != null) {
                        putInt(OWNER, owner)
                    }
                    putString(TEXT, text)
                    putString(CREATED_AT, created_at)
                    putString(UPDATED_AT, updated_at)
                    putString(MODULE, module)
                    if (module_id != null) {
                        putInt(MODULE_ID, module_id)
                    }

                }
            }
    }
}