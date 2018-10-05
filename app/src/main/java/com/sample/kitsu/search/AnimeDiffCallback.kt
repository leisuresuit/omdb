package com.sample.kitsu.search

import androidx.recyclerview.widget.DiffUtil
import com.sample.kitsu.search.model.Anime

class AnimeDiffCallback : DiffUtil.ItemCallback<Anime>() {

    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }
}