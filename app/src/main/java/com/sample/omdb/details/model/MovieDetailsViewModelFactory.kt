package com.sample.omdb.details.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.base.details.model.DetailsRepository
import com.sample.base.details.model.DetailsViewModel

class MovieDetailsViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MovieDetailsViewModel(
        object: DetailsRepository<MovieDetails>() {
            private lateinit var dataSource: MovieDetailsDataSource

            override fun createDataSource(id: String): MovieDetailsDataSource {
                dataSource = MovieDetailsDataSource(id)
                dataSource.fetch()
                return dataSource
            }

            override fun cancelFetch() = dataSource.cancelFetch()
        }) as T
}
