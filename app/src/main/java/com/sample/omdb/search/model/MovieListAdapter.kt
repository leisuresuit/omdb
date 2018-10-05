package com.sample.omdb.search.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.omdb.search.MovieDiffCallback
import com.sample.omdb.databinding.ItemMovieBinding

class MovieListAdapter(
    val listener: Listener
) : PagedListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            with(holder) {
                bind(createOnClickListener(movie.id), movie)
                itemView.tag = movie
            }
        }
    }

    private fun createOnClickListener(movieId: String): View.OnClickListener {
        return View.OnClickListener {
            listener.onClick(movieId, it.findNavController())
        }
    }

    class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Movie) {
            binding.apply {
                clickListener = listener
                movie = item
                executePendingBindings()
            }
        }
    }

    interface Listener {
        fun onClick(movieId: String, navController: NavController)
    }
}