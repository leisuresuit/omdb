package com.sample.omdb.details.model

import com.sample.base.details.model.DetailsDataSource
import com.sample.omdb.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MovieDetailsDataSource(
    val movieId: String
): DetailsDataSource<MovieDetails>() {
    private lateinit var okHttpCall: Call

    override fun fetch() {
        isLoading.postValue(true)
        val request = Request.Builder()
                .url("http://www.omdbapi.com?apikey=${BuildConfig.OMDB_API_KEY}&i=$movieId&plot=full")
                .build()

        val client = OkHttpClient.Builder().build()
        okHttpCall = client.newCall(request)
        okHttpCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoading.postValue(false)
                TODO("handle failure")
            }

            override fun onResponse(call: Call, response: Response) {
                isLoading.postValue(false)
                response.body()?.string()?.let { it ->
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(MovieDetails::class.java)
                    val result = jsonAdapter.fromJson(it)
                    if (result != null) {
                        details.postValue(result)
                    } else {
                        TODO("handle failure")
                    }
                }
            }
        })
    }

    override fun cancelFetch() {
        okHttpCall.cancel()
    }
}
