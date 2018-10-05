package com.sample.base.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedListAdapter
import com.sample.base.search.model.SearchResultViewModel
import com.sample.omdb.R
import kotlinx.android.synthetic.main.fragment_search.*

abstract class SearchFragment<Key, Value, T: SearchResultViewModel<Key, Value>> : Fragment() {
    private lateinit var viewModel: SearchResultViewModel<Key, Value>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity(), createViewModelFactory()).get(getViewModelClass())
    }

    protected abstract fun createViewModelFactory(): ViewModelProvider.NewInstanceFactory
    protected abstract fun getViewModelClass(): Class<out T>

    override fun onResume() {
        super.onResume()
        
        requireActivity().title = getTitle()
    }

    abstract fun getTitle(): CharSequence?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        viewModel.isInitialLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            loading.isVisible = isLoading
        })

        val adapter = createListAdapter()
        resultListView.adapter = adapter
        viewModel.pagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.totalCount.observe(viewLifecycleOwner, Observer {
            if (it > 0) {
                emptyView.isVisible = false
                resultListView.isVisible = true
            } else {
                emptyView.isVisible = true
                resultListView.isVisible = false
            }
        })
    }

    protected abstract fun createListAdapter(): PagedListAdapter<Value, *>

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        initSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initSearchView(menu: Menu) {
        with(((menu.findItem(R.id.search)?.actionView) as SearchView)) {
            val searchManager = context.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean = false

                override fun onQueryTextSubmit(query: String): Boolean {
                    requireActivity().title = getString(R.string.search, query)
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                    viewModel.search(query.trim())
                    return true
                }
            })
        }
    }
}
