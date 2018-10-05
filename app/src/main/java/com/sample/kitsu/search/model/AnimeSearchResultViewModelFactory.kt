package com.sample.kitsu.search.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimeSearchResultViewModelFactory(
    private val repository: AnimeSearchResultRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AnimeSearchResultViewModel(repository) as T
}
