package com.busem.sample.features.home.ui

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.busem.sample.R
import com.busem.sample.common.BaseActivity
import com.busem.sample.common.toast
import com.busem.sample.data.models.Repository
import com.busem.sample.databinding.ActivityHomeBinding
import com.busem.sample.features.home.adapters.RepositoriesAdapter
import com.busem.sample.features.home.viewModels.HomeViewModel
import java.lang.ref.WeakReference

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(
    HomeViewModel::class.java,
    ActivityHomeBinding::inflate
) {

    private val repositoriesAdapter by lazy {
        RepositoriesAdapter(::toggleFavorite, ::toggleFavorite)
    }

    override fun ActivityHomeBinding.setupViews() {

        fun setupRepoList() {
            rvRepositories.apply {
                adapter = repositoriesAdapter
                if (itemDecorationCount < 1) {
                    addItemDecoration(
                        DividerItemDecoration(
                            this@HomeActivity,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }
        }

        fun setupSearch() {
            etSearchRepo.requestFocus()
            etSearchRepo.setOnEditorActionListener { v, actionId, event ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchText = etSearchRepo.text.toString().trim()
                        .takeIf { it.isNotBlank() } ?: run {
                        toast(getString(R.string.please_search_a_repository))
                        return@setOnEditorActionListener false
                    }

                    viewModel.searchResults(searchText)

                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etSearchRepo.windowToken, 0)

                    return@setOnEditorActionListener true
                }

                false
            }
        }

        setupRepoList()
        setupSearch()
    }

    override fun ActivityHomeBinding.observeViewModel() {
        viewModel.apply {
            repos.observe(this@HomeActivity) { repoList ->
                tvNoResults.isVisible = repoList.isNullOrEmpty()

                repositoriesAdapter.submitList(repoList)
                repositoriesAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun navigateToDetails(data: Repository) {

    }

    private fun toggleFavorite(data: Repository) {
        viewModel.toggleFavorite(data)
    }

    companion object {

        fun getIntent(context: WeakReference<Context>) = Intent(
            context.get(),
            HomeActivity::class.java
        )
    }
}