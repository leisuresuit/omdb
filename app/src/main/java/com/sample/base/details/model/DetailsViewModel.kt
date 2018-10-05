package com.sample.base.details.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

open class DetailsViewModel<T>(
    private val repository: DetailsRepository<T>
) : ViewModel() {
    private val wrapper = MutableLiveData<DetailsWrapper<T>>()
    val details = Transformations.switchMap(wrapper) { it.details }!!
    val isLoading = Transformations.switchMap(wrapper) { it.isLoading }!!

    fun fetch(id: String) {
        wrapper.postValue(repository.fetch(id))
    }

    override fun onCleared() {
        super.onCleared()

        repository.cancelFetch()
    }
}
