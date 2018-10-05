package com.sample.reddit.search

import androidx.recyclerview.widget.DiffUtil
import com.sample.reddit.search.model.Reddit

class RedditDiffCallback : DiffUtil.ItemCallback<Reddit>() {

    override fun areItemsTheSame(oldItem: Reddit, newItem: Reddit): Boolean {
        return oldItem.data.id == newItem.data.id
    }

    override fun areContentsTheSame(oldItem: Reddit, newItem: Reddit): Boolean {
        return oldItem == newItem
    }
}