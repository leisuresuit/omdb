package com.sample.kitsu.search

import androidx.navigation.NavController
import androidx.paging.PagedListAdapter
import com.sample.base.search.SearchFragment
import com.sample.kitsu.search.model.Anime
import com.sample.kitsu.search.model.AnimeListAdapter
import com.sample.kitsu.search.model.AnimeSearchResultRepository
import com.sample.kitsu.search.model.AnimeSearchResultViewModel
import com.sample.kitsu.search.model.AnimeSearchResultViewModelFactory
import com.sample.omdb.R

class AnimeSearchFragment : SearchFragment<Int, Anime, AnimeSearchResultViewModel>(), AnimeListAdapter.Listener {
    override fun getTitle(): CharSequence? {
        return getString(R.string.search_kitsu)
    }

    override fun createViewModelFactory() = AnimeSearchResultViewModelFactory(AnimeSearchResultRepository)
    override fun getViewModelClass(): Class<out AnimeSearchResultViewModel> = AnimeSearchResultViewModel::class.java

    override fun createListAdapter(): PagedListAdapter<Anime, *> = AnimeListAdapter(this)

    override fun onClick(animeId: String, navController: NavController) {
        val action = AnimeSearchFragmentDirections.actionAnimeSearchFragmentToAnimeDetailsFragment(animeId)
        navController.navigate(action)
    }
}
