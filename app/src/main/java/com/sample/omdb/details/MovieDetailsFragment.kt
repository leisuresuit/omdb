package com.sample.omdb.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sample.base.details.DetailsFragment
import com.sample.omdb.databinding.FragmentMovieDetailsBinding
import com.sample.omdb.details.model.MovieDetails
import com.sample.omdb.details.model.MovieDetailsViewModel
import com.sample.omdb.details.model.MovieDetailsViewModelFactory
import com.sample.omdb.search.model.MovieSearchResultViewModel

class MovieDetailsFragment : DetailsFragment<MovieDetails>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movieId = MovieDetailsFragmentArgs.fromBundle(arguments).movieId
        val searchResultViewModel = ViewModelProviders.of(requireActivity()).get(MovieSearchResultViewModel::class.java)
        searchResultViewModel.pagedList.value?.find { movie -> movie.id == movieId } ?.let { movie ->
            binding.movie = movie
            requireActivity().title = movie.title
        }

        viewModel.details.observe(this, Observer {
            binding.movieDetails = it
        })
        return binding.root
    }

    override fun createViewModelFactory(): ViewModelProvider.NewInstanceFactory = MovieDetailsViewModelFactory()
    override fun getViewModelClass() = MovieDetailsViewModel::class.java

    override fun getItemId(): String = MovieDetailsFragmentArgs.fromBundle(arguments).movieId
}
