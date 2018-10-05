package com.sample.kitsu.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sample.kitsu.search.model.AnimeSearchResultRepository
import com.sample.kitsu.search.model.AnimeSearchResultViewModel
import com.sample.kitsu.search.model.AnimeSearchResultViewModelFactory
import com.sample.omdb.databinding.FragmentAnimeDetailsBinding

class AnimeDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentAnimeDetailsBinding.inflate(inflater, container, false)
        val animeId = AnimeDetailsFragmentArgs.fromBundle(arguments).animeId
        val searchResultViewModel = ViewModelProviders.of(requireActivity(),
                AnimeSearchResultViewModelFactory(AnimeSearchResultRepository)).get(AnimeSearchResultViewModel::class.java)
        searchResultViewModel.pagedList.value?.find { anime -> anime.id == animeId }?.let {
            binding.anime = it
            requireActivity().title = it.attributes.title
        }

        return binding.root
    }
}
