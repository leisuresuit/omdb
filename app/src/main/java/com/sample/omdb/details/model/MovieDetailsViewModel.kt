package com.sample.omdb.details.model

import com.sample.base.details.model.DetailsRepository
import com.sample.base.details.model.DetailsViewModel

/**
* An explicit class is needed to differentiate between different view models of the same base class
* in the {@code ViewModelProviders}
*/
class MovieDetailsViewModel(
    repository: DetailsRepository<MovieDetails>
): DetailsViewModel<MovieDetails>(repository)
