package com.sample.reddit.search

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import androidx.paging.PagedListAdapter
import com.sample.base.search.SearchFragment
import com.sample.omdb.R
import com.sample.reddit.search.model.Reddit
import com.sample.reddit.search.model.RedditListAdapter
import com.sample.reddit.search.model.RedditSearchResultRepository
import com.sample.reddit.search.model.RedditSearchResultViewModel
import com.sample.reddit.search.model.RedditSearchResultViewModelFactory

class RedditSearchFragment : SearchFragment<Int, Reddit, RedditSearchResultViewModel>(), RedditListAdapter.Listener {
    override fun getTitle(): CharSequence? {
        return getString(R.string.search_reddit)
    }

    override fun createViewModelFactory() = RedditSearchResultViewModelFactory(RedditSearchResultRepository)
    override fun getViewModelClass(): Class<out RedditSearchResultViewModel> = RedditSearchResultViewModel::class.java

    override fun createListAdapter(): PagedListAdapter<Reddit, *> = RedditListAdapter(this)

    override fun onClick(redditId: String, navController: NavController) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com$redditId"))
        startActivity(intent)
    }
}
