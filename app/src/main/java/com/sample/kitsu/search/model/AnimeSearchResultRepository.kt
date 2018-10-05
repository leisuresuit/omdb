package com.sample.kitsu.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.sample.base.search.model.SearchResultDataSourceFactory
import com.sample.base.search.model.SearchResultRepository
import com.sample.kitsu.search.model.Anime

object AnimeSearchResultRepository : SearchResultRepository<Int, Anime>() {
    override fun createDataSourceFactory(query: String) =
        object : SearchResultDataSourceFactory<Int, Anime>(query) {
            override fun createDataSource(query: String): SearchResultDataSource<Int, Anime> = AnimeSearchResultDataSource(query)
        }

    override fun getPageSize(): Int = PAGE_SIZE
}
