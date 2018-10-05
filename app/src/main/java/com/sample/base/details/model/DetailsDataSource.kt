package com.sample.base.details.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class DetailsDataSource<T> {
    protected val isLoading = MutableLiveData<Boolean>()
    protected val details = MutableLiveData<T>()

    abstract fun fetch()
    abstract fun cancelFetch()
    fun isLoading(): LiveData<Boolean> = isLoading
    fun details(): LiveData<T> = details
}
