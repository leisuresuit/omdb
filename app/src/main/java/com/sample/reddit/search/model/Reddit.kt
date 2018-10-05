package com.sample.reddit.search.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reddit (
    val data: RedditData
)

@JsonClass(generateAdapter = true)
data class RedditData(
    @Json(name="permalink") val id: String = "",
    val title: String? = "",
    val url: String = "",
    val author: String? = "",
    @Json(name="num_comments") val numComments: Int = 0,
    @Json(name="selftext") val selfText: String? = "",
    val preview: Preview?
)

@JsonClass(generateAdapter = true)
data class Preview(
    val images: List<ImageContainer>? = listOf()
)

@JsonClass(generateAdapter = true)
data class ImageContainer (
    val source: Image?
)

@JsonClass(generateAdapter = true)
data class Image (
    val url: String? = "",
    val width: Int = 0,
    val height: Int = 0
)
