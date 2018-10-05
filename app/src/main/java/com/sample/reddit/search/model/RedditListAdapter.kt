package com.sample.reddit.search.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.reddit.search.RedditDiffCallback
import com.sample.omdb.databinding.ItemRedditBinding

class RedditListAdapter(
    val listener: Listener
) : PagedListAdapter<Reddit, RedditListAdapter.ViewHolder>(RedditDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRedditBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { reddit ->
            with(holder) {
                bind(createOnClickListener(reddit.data.id), reddit)
                itemView.tag = reddit
            }
        }
    }

    private fun createOnClickListener(redditId: String): View.OnClickListener {
        return View.OnClickListener {
            listener.onClick(redditId, it.findNavController())
        }
    }

    class ViewHolder(
        private val binding: ItemRedditBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Reddit) {
            binding.apply {
                clickListener = listener
                reddit = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onClick(animeId: String, navController: NavController)
    }
}