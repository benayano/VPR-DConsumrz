package com.example.vprdconsumrz.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.data.CommentDataView
import com.example.vprdconsumrz.model.repository.AccountRepository
import com.example.vprdconsumrz.model.repository.UserDetails
import com.example.vprdconsumrz.view.adapters.CommentAdapter
import com.example.vprdconsumrz.view.fragments.EditComment.Companion.newInstanceEditComment
import com.example.vprdconsumrz.viewModel.MainViewModel
import com.example.vprdconsumrz.viewModel.MainViewModelFactory


private const val ID_POST = "postId"
private const val TITLE_POST = "postId"
private const val POST_BODY = "postId"

class PostAndComments : Fragment(R.layout.fragment_post_and_comments) {

    private val mainViewModel: MainViewModel by activityViewModels {
        val userDetails = UserDetails(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))
        MainViewModelFactory(AccountRepository, userDetails)
    }

    private val rvComment: RecyclerView by lazy { requireView().findViewById(R.id.rvComments) }
    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(
            this::deleteComment,
            this::editComment
        )
    }
    private val ivAddComment: ImageView by lazy { requireView().findViewById(R.id.ivAddComment) }
    private val ivBackComment: ImageView by lazy { requireView().findViewById(R.id.ivBackComment) }
    private val tvTitlePost: TextView by lazy { requireView().findViewById(R.id.titlePostWithComment) }
    private val tvBodyPost: TextView by lazy { requireView().findViewById(R.id.bodyPostWithComment) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Post And Comments🌟"


        tvTitlePost.text = postTitle
        tvBodyPost.text = postBody

        rvComment.apply {
            adapter = commentAdapter.apply {
                LinearLayoutManager(requireContext())
            }
        }

        ivBackComment.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, MainUser())
            }
        }
        ivAddComment.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.mainContainerFragment, EditComment())
            }
        }

        mainViewModel.getComment().observe(viewLifecycleOwner) { commentList ->
            commentAdapter.submitList(commentList.filter { comment->
                comment.module_id == postId
            })
        }

    }

    private fun deleteComment(commentDataView: CommentDataView) {
        mainViewModel.deleteComments(commentDataView.id)
    }

    private fun editComment(commentDataView: CommentDataView) {
        ivAddComment.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.mainContainerFragment, newInstanceEditComment(
                        id = commentDataView.id,
                        owner = commentDataView.owner,// User.id (publisher)
                        text = commentDataView.text,
                        created_at = commentDataView.created_at, // (datetime)
                        updated_at = commentDataView.updated_at, // (datetime)
                        module = commentDataView.module, // posts
                        module_id = commentDataView.module_id,// post.id
                    )
                )
            }
        }
    }

    private var postId :Int? = null
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
        fun newInstancePostAntComment(postId: Int, postTitle: String, postBody: String) =
            PostAndComments().apply {
                arguments = Bundle().apply {
                    putInt(ID_POST, postId)
                    putString(TITLE_POST, postTitle)
                    putString(POST_BODY, postBody)
                }
            }
    }
}