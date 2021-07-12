package com.busem.sample.features.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.sample.common.BaseViewModel
import com.busem.sample.common.EMPTY_STRING
import com.busem.sample.common.SingleLiveEvent
import com.busem.sample.data.models.Repository
import com.busem.sample.data.repositories.GithubRepo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val githubRepo by lazy { GithubRepo() }

    private val _searchTerm by lazy { SingleLiveEvent<String>() }

    private val _repos by lazy { MutableLiveData<List<Repository>>() }
    val repos: LiveData<List<Repository>> by lazy { _repos }

    fun searchResults(searchTerm: String) {
        _searchTerm.postValue(searchTerm)
        ioScope.launch {
            withContext(ioScope.coroutineContext) {
                githubRepo.fetchRepositories(
                    searchTerm
                )
            }?.let { repos ->
                _repos.postValue(repos)
                return@let
            } ?: run {
                _repos.postValue(githubRepo.getRepositories(searchTerm))
                return@run
            }
        }
    }

    fun toggleFavorite(data: Repository) {
        ioScope.launch {
            githubRepo.saveRepository(data)
            _repos.postValue(githubRepo.getRepositories(_searchTerm.value ?: EMPTY_STRING))
        }
    }
}