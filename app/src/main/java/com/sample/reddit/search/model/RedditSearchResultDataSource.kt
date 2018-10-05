package com.sample.reddit.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

const val PAGE_SIZE = 100

class RedditSearchResultDataSource(
    query: String
) : SearchResultDataSource<Int, Reddit>(query) {
    private lateinit var okHttpCall: Call

    private fun load(successHandler: (Int, List<Reddit>) -> Unit, failureHandler: (IOException?) -> Unit) {
        val request = Request.Builder()
                .url("https://www.reddit.com/r/$query/hot.json?limit=$PAGE_SIZE")
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
                    val jsonAdapter = moshi.adapter(RedditSearchResult::class.java)
                    val searchResult = jsonAdapter.fromJson(it)
                    if (searchResult != null) {
                        val count = searchResult.data.reddits.size
                        totalCount.postValue(count)
                        successHandler(count, searchResult.data.reddits)
                    } else {
                        failureHandler(null)
                    }
                }
            }
        })
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Reddit>) {
        isInitialLoading.postValue(true)
        load(
            { totalCount, movies ->
                isInitialLoading.postValue(false)
                callback.onResult(movies, 0, totalCount, null, null)
            },
            {
                isInitialLoading.postValue(false)
                TODO("handle failure")
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Reddit>) {
        // ignored, since we only ever load one page
    }

    override fun cancelSearch() {
        okHttpCall.cancel()
    }
}
