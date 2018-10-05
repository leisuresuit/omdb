package com.sample.base.search.model

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import java.util.concurrent.Executors

abstract class SearchResultRepository<Key, Value> {
    private lateinit var sourceFactory: SearchResultDataSourceFactory<Key, Value>

    @MainThread
    fun search(query: String): SearchResultWrapper<Value> {
        sourceFactory = createDataSourceFactory(query)
        val livePagedList = LivePagedListBuilder(sourceFactory, getPageSize())
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build()

        val sourceLiveData = sourceFactory.sourceLiveData()
        return SearchResultWrapper(
                Transformations.switchMap(sourceLiveData) { it.totalCount() },
                livePagedList,
                Transformations.switchMap(sourceLiveData) { it.isInitialLoading() },
                Transformations.switchMap(sourceLiveData) { it.isLoading() }
        )
    }

    fun cancelSearch() = sourceFactory.cancelSearch()
    protected abstract fun createDataSourceFactory(query: String): SearchResultDataSourceFactory<Key, Value>
    protected abstract fun getPageSize(): Int
}