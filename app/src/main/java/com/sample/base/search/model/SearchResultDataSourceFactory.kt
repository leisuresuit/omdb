package com.sample.base.search.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class SearchResultDataSourceFactory<Key, Value>(
    private val query: String
) : DataSource.Factory<Key, Value>() {
    lateinit var dataSource: SearchResultDataSource<Key, Value>
    private val sourceLiveData = MutableLiveData<SearchResultDataSource<Key, Value>>()

    override fun create(): DataSource<Key, Value> {
        dataSource = createDataSource(query)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    protected abstract fun createDataSource(query: String): SearchResultDataSource<Key, Value>

    fun sourceLiveData(): LiveData<SearchResultDataSource<Key, Value>> = sourceLiveData
    fun cancelSearch() = dataSource.cancelSearch()
}
