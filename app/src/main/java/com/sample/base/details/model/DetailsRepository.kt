package com.sample.base.details.model

import androidx.annotation.MainThread

/**
 * T    Class of the details object
 */
abstract class DetailsRepository<T> {
    @MainThread
    fun fetch(id: String): DetailsWrapper<T> {
        with(createDataSource(id)) {
            return DetailsWrapper(details(), isLoading())
        }
    }

    abstract fun cancelFetch()
    abstract fun createDataSource(id: String): DetailsDataSource<T>
}
