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
import com.example.vprdconsumrz.data.PostData

class PostAdapters(
    private var deleteItem: (postData: PostData) -> Unit = {},
    private val getPostScreen: (postData: PostData) -> Unit = {},
) :
    ListAdapter<PostData, PostAdapters.ViewHolder>(DaysDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.post, parent, false)
        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(postView: View) : RecyclerView.ViewHolder(postView) {
        private val tvTitlePost: TextView = postView.findViewById(R.id.tvTitlePost)
        private val tvBodyPost: TextView = postView.findViewById(R.id.tvBodyPost)
        private val ivDeletePost: ImageView = postView.findViewById(R.id.ivDeletePost)

        fun bind(postData: PostData) {
            tvTitlePost.text = postData.title
            tvBodyPost.text = postData.body

            tvBodyPost.setOnClickListener {
                getPostScreen(postData)
            }

            ivDeletePost.setOnClickListener {
                deleteItem(postData)
            }
        }


    }

    class DaysDiffUtil : DiffUtil.ItemCallback<PostData>() {
        override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem == newItem
        }

    }
}

