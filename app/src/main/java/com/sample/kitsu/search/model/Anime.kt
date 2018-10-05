package com.sample.kitsu.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Anime (
    val id: String = "",
    val attributes: Attributes
)

@JsonClass(generateAdapter = true)
data class Attributes(
    @Json(name="canonicalTitle") val title: String? = "",
    val startDate: String? = "",
    val endDate: String? = "",
    val posterImage: PosterImage?,
    @Json(name="synopsis") val plot: String? = "",
    val averageRating: String? = ""
)

@JsonClass(generateAdapter = true)
data class PosterImage (
    val tiny: String? = "",
    val small: String? = "",
    val medium: String? = "",
    val large: String? = "",
    val original: String = ""
)
