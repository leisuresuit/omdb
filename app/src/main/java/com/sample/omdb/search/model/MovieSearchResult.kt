package com.sample.omdb.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSearchResult(
    @Json(name="totalResults") val totalCount: Int = 0,
    @Json(name="Search") val movies: List<Movie> = listOf()
)
