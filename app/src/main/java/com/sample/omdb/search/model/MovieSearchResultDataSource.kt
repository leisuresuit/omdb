package com.sample.omdb.search.model

import com.sample.base.search.model.SearchResultDataSource
import com.sample.omdb.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

const val PAGE_SIZE = 10

class MovieSearchResultDataSource(
    query: String
) : SearchResultDataSource<Int, Movie>(query) {
    private lateinit var okHttpCall: Call

    private fun load(pageIndex: Int, successHandler: (Int, List<Movie>) -> Unit, failureHandler: (IOException?) -> Unit) {
        val request = Request.Builder()
                .url("http://www.omdbapi.com?apikey=${BuildConfig.OMDB_API_KEY}&s=$query&page=$pageIndex")
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
                    val jsonAdapter = moshi.adapter(MovieSearchResult::class.java)
                    val searchResult = jsonAdapter.fromJson(it)
                    if (searchResult != null) {
                        totalCount.postValue(searchResult.totalCount)
                        successHandler(searchResult.totalCount, searchResult.movies)
                    } else {
                        failureHandler(null)
                    }
                }
            }
        })
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        isInitialLoading.postValue(true)
        load(1,
            { totalCount, movies ->
                isInitialLoading.postValue(false)
                callback.onResult(movies, 0, totalCount, null, if (PAGE_SIZE <= totalCount) 2 else null)
            },
            {
                isInitialLoading.postValue(false)
                TODO("handle failure")
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        isLoading.postValue(true)
        load(params.key,
            { totalCount, movies ->
                isLoading.postValue(false)
                callback.onResult(movies, if (params.key * PAGE_SIZE <= totalCount) params.key + 1 else null)
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
