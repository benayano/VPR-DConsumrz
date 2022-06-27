package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.data.PostData
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.view.adapters.PostAdapters
import com.example.vprdconsumrz.view.fragments.EditPost.Companion.newInstanceEditPost
import com.example.vprdconsumrz.view.fragments.PostAndComments.Companion.newInstancePostAntComment
import com.example.vprdconsumrz.viewModel.MainViewModel
import com.example.vprdconsumrz.viewModel.MainViewModelFactory


private const val ID = "id"
private const val EMAIL = "email"
private const val PASSWORD = "password"


class MainUser : Fragment(R.layout.fragment_main_user) {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(AccountRepository)
    }

    private var id: Int? = null
    private var email: String? = null
    private var password: String? = null


    private val rvPosts: RecyclerView by lazy { requireView().findViewById(R.id.rvPosts) }
    private val postAdapters: PostAdapters by lazy {
        PostAdapters(
            this::deletePost,
            this::getPostScreen
        )
    }
    private val ivAddPost: ImageView by lazy { requireView().findViewById(R.id.ivAddPost) }
    private val tvMain: TextView by lazy { requireView().findViewById(R.id.tvPosts) }

    private fun deletePost(postData: PostData) {
        mainViewModel.deletePost(postData.id)
    }

    private fun getPostScreen(postData: PostData) {
        parentFragmentManager.commit {
            replace(
                R.id.mainContainerFragment,
                newInstancePostAntComment(
                    postId = postData.id,
                    postTitle = postData.title,
                    postBody = postData.body

                )
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvMain.text = "$id,$email,$password"

        super.onViewCreated(view, savedInstanceState)
        rvPosts.apply {
            adapter = postAdapters.apply {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        ivAddPost.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, AddingNewPost())
            }
        }

        mainViewModel.getAllPosts().observe(viewLifecycleOwner) {
            postAdapters.submitList(it)
        }
    }

    companion object {

        @JvmStatic
        fun newInstanceMainUser(id: Int, email: String, password: String?): Fragment =
            MainUser().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                    putString(EMAIL, email)
                    putString(PASSWORD, password)
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
            email = it.getString(EMAIL)
            password = it.getString(PASSWORD)
        }
    }


}