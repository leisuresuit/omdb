package com.sample.reddit.search.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RedditSearchResultViewModelFactory(
    private val repository: RedditSearchResultRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = RedditSearchResultViewModel(repository) as T
}
