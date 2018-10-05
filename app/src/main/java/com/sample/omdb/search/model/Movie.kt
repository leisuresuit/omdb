package com.sample.omdb.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie (
    @Json(name="imdbID") val id: String = "",
    @Json(name="Title") val title: String? = "",
    @Json(name="Year") val year: String? = "",
    @Json(name="Poster") val posterUrl: String? = "",
    @Json(name="Plot") val plot: String? = ""
)
