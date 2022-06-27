package com.example.vprdconsumrz.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vprdconsumrz.R
import com.example.vprdconsumrz.data.CommentDataView

class CommentAdapter(
    private var deleteComment: (CommentDataView) -> Unit = {},
    private val editComment: (CommentDataView) -> Unit = {}
) :
    ListAdapter<CommentDataView, CommentAdapter.ViewHolder>(DaysDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val commentView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment, parent, false)
        return ViewHolder(commentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(postView: View) : RecyclerView.ViewHolder(postView) {
        private val createdCommentName: TextView = postView.findViewById(R.id.createdCommentName)
        private val commentBody: TextView = postView.findViewById(R.id.commentBody)
        private val ivDelete: ImageView = postView.findViewById(R.id.ivDeleteComment)

        fun bind(commentDataView: CommentDataView) {
            createdCommentName.text = commentDataView.created_at
            commentBody.text = commentDataView.text

            commentBody.setOnClickListener {
                editComment(commentDataView)
            }

            ivDelete.setOnClickListener {
                deleteComment(commentDataView)
            }
        }
    }

    class DaysDiffUtil : DiffUtil.ItemCallback<CommentDataView>() {
        override fun areItemsTheSame(oldItem: CommentDataView, newItem: CommentDataView): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: CommentDataView,
            newItem: CommentDataView
        ): Boolean {
            return oldItem == newItem
        }

    }
}

