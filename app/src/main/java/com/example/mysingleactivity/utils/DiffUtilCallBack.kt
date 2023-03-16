package com.example.mysingleactivity.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.mysingleactivity.models.Post

class DiffUtilCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
        // return oldItem.key == newItem.key //old string code
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.title == newItem.title
                && oldItem.score == newItem.score
                && oldItem.commentCount == newItem.commentCount
    }
}