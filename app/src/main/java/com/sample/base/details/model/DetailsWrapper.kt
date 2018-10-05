package com.sample.base.details.model

import androidx.lifecycle.LiveData

/**
 * T    Class of the details object
 */
data class DetailsWrapper<T>(
    val details: LiveData<T>,
    /** {@code true} if loading data */
    val isLoading: LiveData<Boolean>
)
