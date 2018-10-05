package com.sample.reddit.search.model

import com.sample.base.search.model.SearchResultViewModel

/**
 * An explicit class is needed to differentiate between different view models of the same base class
 * in the {@code ViewModelProviders}
 */
class RedditSearchResultViewModel (
    repository: RedditSearchResultRepository
) : SearchResultViewModel<Int, Reddit>(repository)
