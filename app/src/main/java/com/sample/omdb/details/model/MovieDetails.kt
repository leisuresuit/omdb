package com.sample.omdb.details.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails (
    @Json(name="imdbID") val id: String = "",
    @Json(name="Title") val title: String? = "",
    @Json(name="Year") val year: String? = "",
    @Json(name="Poster") val posterUrl: String? = "",
    @Json(name="Rated") val rating: String? = "",
    @Json(name="Runtime") val runtime: String? = "",
    @Json(name="Actors") val actors: String? = "",
    @Json(name="Plot") val plot: String? = ""
)
