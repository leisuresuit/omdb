package com.sample.base.search.model

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource

abstract class SearchResultDataSource<Key, Value>(
    protected val query: String
) : PageKeyedDataSource<Key, Value>() {
    protected val isInitialLoading = MutableLiveData<Boolean>()
    protected val isLoading = MutableLiveData<Boolean>()
    protected val totalCount = MutableLiveData<Int>()

    @CallSuper
    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        // ignored, since we only ever append to our initial load
    }

    fun isInitialLoading(): LiveData<Boolean> = isInitialLoading
    fun isLoading(): LiveData<Boolean> = isLoading
    fun totalCount(): LiveData<Int> = totalCount
    abstract fun cancelSearch()
}
