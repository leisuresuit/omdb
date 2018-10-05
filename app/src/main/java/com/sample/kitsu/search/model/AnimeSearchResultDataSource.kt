package com.sample.kitsu.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.sample.kitsu.search.model.Anime
import com.sample.omdb.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

const val PAGE_SIZE = 20

class AnimeSearchResultDataSource(
    query: String
) : SearchResultDataSource<Int, Anime>(query) {
    private lateinit var okHttpCall: Call

    private fun load(pageIndex: Int, successHandler: (Int, List<Anime>) -> Unit, failureHandler: (IOException?) -> Unit) {
        val request = Request.Builder()
                .url("https://kitsu.io/api/edge/anime?filter[text]=$query&page[limit]=$PAGE_SIZE&page[offset]$pageIndex")
                .build()

        val client = OkHttpClient.Builder().build()
        okHttpCall = client.newCall(request)
        okHttpCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureHandler(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.string()?.let { it ->
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(AnimeSearchResult::class.java)
                    val searchResult = jsonAdapter.fromJson(it)
                    if (searchResult != null) {
                        totalCount.postValue(searchResult.meta.totalCount)
                        successHandler(searchResult.meta.totalCount, searchResult.animes)
                    } else {
                        failureHandler(null)
                    }
                }
            }
        })
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Anime>) {
        isInitialLoading.postValue(true)
        load(1,
            { totalCount, movies ->
                isInitialLoading.postValue(false)
                callback.onResult(movies, 0, totalCount, null, 0)
            },
            {
                isInitialLoading.postValue(false)
                TODO("handle failure")
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Anime>) {
        isLoading.postValue(true)
        load(params.key,
            { totalCount, movies ->
                isLoading.postValue(false)
                callback.onResult(movies, if (params.key * PAGE_SIZE <= totalCount) params.key + PAGE_SIZE else null)
            },
            {
                isLoading.postValue(false)
                TODO("handle failure")
            }
        )
    }

    override fun cancelSearch() {
        okHttpCall.cancel()
    }
}
