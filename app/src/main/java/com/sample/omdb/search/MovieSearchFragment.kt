package com.sample.omdb.search

import androidx.navigation.NavController
import androidx.paging.PagedListAdapter
import com.sample.base.search.SearchFragment
import com.sample.omdb.R
import com.sample.omdb.search.model.Movie
import com.sample.omdb.search.model.MovieListAdapter
import com.sample.omdb.search.model.MovieSearchResultRepository
import com.sample.omdb.search.model.MovieSearchResultViewModel
import com.sample.omdb.search.model.MovieSearchResultViewModelFactory

class MovieSearchFragment : SearchFragment<Int, Movie, MovieSearchResultViewModel>(), MovieListAdapter.Listener {
    override fun getTitle(): CharSequence? {
        return getString(R.string.search_omdb)
    }

    override fun createViewModelFactory() = MovieSearchResultViewModelFactory(MovieSearchResultRepository)
    override fun getViewModelClass(): Class<out MovieSearchResultViewModel> = MovieSearchResultViewModel::class.java

    override fun createListAdapter(): PagedListAdapter<Movie, *> = MovieListAdapter(this)

    override fun onClick(movieId: String, navController: NavController) {
        val action = MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailsFragment(movieId)
        navController.navigate(action)
    }
}
