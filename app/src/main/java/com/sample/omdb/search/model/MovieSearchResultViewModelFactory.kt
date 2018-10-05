package com.sample.omdb.search.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieSearchResultViewModelFactory(
    private val repository: MovieSearchResultRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MovieSearchResultViewModel(repository) as T
}
