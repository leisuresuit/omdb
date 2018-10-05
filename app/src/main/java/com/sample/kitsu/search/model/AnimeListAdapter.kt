package com.sample.kitsu.search.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.kitsu.search.AnimeDiffCallback
import com.sample.omdb.databinding.ItemAnimeBinding

class AnimeListAdapter(
    val listener: Listener
) : PagedListAdapter<Anime, AnimeListAdapter.ViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { anime ->
            with(holder) {
                bind(createOnClickListener(anime.id), anime)
                itemView.tag = anime
            }
        }
    }

    private fun createOnClickListener(animeId: String): View.OnClickListener {
        return View.OnClickListener {
            listener.onClick(animeId, it.findNavController())
        }
    }

    class ViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Anime) {
            binding.apply {
                clickListener = listener
                anime = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onClick(animeId: String, navController: NavController)
    }
}