package com.sample.reddit.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.sample.base.search.model.SearchResultDataSourceFactory
import com.sample.base.search.model.SearchResultRepository

object RedditSearchResultRepository : SearchResultRepository<Int, Reddit>() {
    override fun createDataSourceFactory(query: String) =
        object : SearchResultDataSourceFactory<Int, Reddit>(query) {
            override fun createDataSource(query: String): SearchResultDataSource<Int, Reddit> = RedditSearchResultDataSource(query)
        }

    override fun getPageSize(): Int = PAGE_SIZE
}
