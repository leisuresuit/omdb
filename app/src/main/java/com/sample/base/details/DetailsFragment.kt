package com.sample.base.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sample.base.details.model.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*

abstract class DetailsFragment<T> : Fragment() {
    protected lateinit var viewModel: DetailsViewModel<T>

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(requireActivity(), createViewModelFactory()).get(getViewModelClass())
        viewModel.isLoading.observe(this, Observer { isLoading ->
            loading.isVisible = isLoading
        })
        viewModel.fetch(getItemId())
        return null
    }

    protected abstract fun createViewModelFactory(): ViewModelProvider.NewInstanceFactory
    protected abstract fun getViewModelClass(): Class<out DetailsViewModel<T>>

    protected abstract fun getItemId(): String
}
