package id.indocyber.newsapi.fragment.sources

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponse
import id.indocyber.common.base_class.BaseFragment
import id.indocyber.common.extension.setGone
import id.indocyber.common.extension.setVisible
import id.indocyber.newsapi.R
import id.indocyber.newsapi.databinding.FragmentSourcesBinding
import id.indocyber.newsapi.view_model.SourcesViewModel

@AndroidEntryPoint
class SourcesFragment : BaseFragment<SourcesViewModel, FragmentSourcesBinding>() {
    override val vm: SourcesViewModel by viewModels()
    override val layoutResourceId = R.layout.fragment_sources
    private val adapter = SourcesAdapter(
        ::getSelectedSources,
        ::setNextButtonVisibility
    )

    override fun initBinding(binding: FragmentSourcesBinding) {
        binding.recycler.adapter = adapter
        binding.textInputSearch.addTextChangedListener {
            vm.searchText.value = it.toString()
        }
        binding.fabNext.setOnClickListener {
            val toArticlesFragment =
                SourcesFragmentDirections.actionSourcesFragmentToArticlesFragment(
                    selectedSources = vm.selectedSourceIds.joinToString(",")
                )
            it.findNavController().navigate(toArticlesFragment)
        }
        val selectedCategory = SourcesFragmentArgs.fromBundle(arguments as Bundle).selectedCategory
        if (vm.getSourcesState.value == null) {
            vm.getSources(selectedCategory)
        }
        binding.btnRetry.setOnClickListener {
            vm.getSources(selectedCategory)
        }
        setNextButtonVisibility()

        vm.getSourcesState.observe(this) { appResponse ->
            when (appResponse) {
                is AppResponse.AppResponseSuccess -> {
                    binding.progressBar.setGone()
                    binding.btnRetry.setGone()
                    filterBySearch()
                }
                is AppResponse.AppResponseError -> {
                    binding.progressBar.setGone()
                    binding.btnRetry.setVisible()

                }
                is AppResponse.AppResponseLoading -> {
                    binding.progressBar.setVisible()
                    binding.btnRetry.setGone()
                }
            }
        }
        vm.searchText.observe(this) {
            filterBySearch()
        }
    }

    private fun getSelectedSources() = vm.selectedSourceIds

    private fun setNextButtonVisibility() {
        when (vm.selectedSourceIds.isEmpty()) {
            true -> binding.fabNext.setGone()
            false -> binding.fabNext.setVisible()
        }
    }

    private fun filterBySearch() {
        vm.filterBySearch()
        adapter.submitSourceList(vm.sourceList)
    }
}