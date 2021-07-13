package com.busem.sample.features.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.busem.data.models.Repository
import com.busem.sample.R
import com.busem.sample.common.BaseAbstractFragment
import com.busem.sample.common.ViewModelFactory
import com.busem.sample.common.toast
import com.busem.sample.databinding.FragmentHomeBinding

class HomeFragment :
    BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private val repositoriesAdapter by lazy {
        RepositoriesAdapter(::selectedRepo, ::toggleFavorite)
    }

    override fun setViewModel(): HomeViewModel =
        ViewModelProvider(this@HomeFragment, ViewModelFactory { HomeViewModel() })
            .get(HomeViewModel::class.java)

    override fun setupViews(): FragmentHomeBinding.() -> Unit = {

        fun setupRepoList() {
            rvRepositories.apply {
                adapter = repositoriesAdapter
                if (itemDecorationCount < 1) {
                    addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }
        }

        fun setupSearch() {
            etSearchRepo.requestFocus()
            etSearchRepo.setOnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchText = etSearchRepo.text.toString().trim()
                        .takeIf { it.isNotBlank() } ?: run {
                        toast(getString(R.string.please_search_a_repository))
                        return@setOnEditorActionListener false
                    }

                    viewModel.searchResults(searchText)

                    val imm: InputMethodManager =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etSearchRepo.windowToken, 0)

                    return@setOnEditorActionListener true
                }

                false
            }
        }

        setupRepoList()
        setupSearch()

    }

    override fun setupObservers(): HomeViewModel.() -> Unit = {

        repos.observe(viewLifecycleOwner) { repoList ->
            mBinding.tvNoResults.isVisible = repoList.isNullOrEmpty()

            repositoriesAdapter.submitList(repoList)
            repositoriesAdapter.notifyDataSetChanged()
        }

    }

    private fun toggleFavorite(data: Repository) {
        viewModel.toggleFavorite(data)
    }

    private fun selectedRepo(data: Repository) {
        viewModel.toggleFavorite(data)
    }
}
