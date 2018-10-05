package com.sample.base.search.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class SearchResultWrapper<T>(
    /** total number of results */
    val totalCount: LiveData<Int>,
    /** the LiveData of paged lists for the UI to observe */
    val pagedList: LiveData<PagedList<T>>,
    /** {@code true} if loading initial data */
    val isInitialLoading: LiveData<Boolean>,
    /** {@code true} if loading additional data */
    val isLoading: LiveData<Boolean>
)
