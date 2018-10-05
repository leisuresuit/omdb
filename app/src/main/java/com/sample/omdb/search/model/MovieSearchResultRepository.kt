package com.sample.omdb.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.sample.base.search.model.SearchResultDataSourceFactory
import com.sample.base.search.model.SearchResultRepository

object MovieSearchResultRepository : SearchResultRepository<Int, Movie>() {
    override fun createDataSourceFactory(query: String) =
        object : SearchResultDataSourceFactory<Int, Movie>(query) {
            override fun createDataSource(query: String): SearchResultDataSource<Int, Movie> = MovieSearchResultDataSource(query)
        }

    override fun getPageSize(): Int = PAGE_SIZE
}
