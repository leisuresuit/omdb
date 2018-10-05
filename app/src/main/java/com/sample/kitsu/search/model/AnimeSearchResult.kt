package com.sample.kitsu.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeSearchResult(
    val meta: Meta,
    @Json(name="data") val animes: List<Anime> = listOf()
)

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name="count") val totalCount: Int = 0
)
