package com.sample.base.search.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel

open class SearchResultViewModel<Key, Value>(
    private val repository: SearchResultRepository<Key, Value>
) : ViewModel() {
    private var query: String? = null
    private val searchResult = MutableLiveData<SearchResultWrapper<Value>>()
    val pagedList = switchMap(searchResult) { it.pagedList }!!
    val totalCount = switchMap(searchResult) { it.totalCount }!!
    val isInitialLoading = switchMap(searchResult) { it.isInitialLoading }!!
    val isLoading = switchMap(searchResult) { it.isLoading }!!

    fun search(query: String): Boolean {
        if (this.query == query) {
            return false
        }
        this.query = query
        searchResult.postValue(repository.search(query))
        return true
    }

    override fun onCleared() {
        super.onCleared()

        repository.cancelSearch()
    }
}
