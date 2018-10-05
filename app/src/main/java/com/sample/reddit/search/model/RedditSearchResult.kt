package com.sample.reddit.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditSearchResult(
    val data: RedditSearchResultData
)

@JsonClass(generateAdapter = true)
data class RedditSearchResultData(
    @Json(name="children") val reddits: List<Reddit> = listOf()
)
