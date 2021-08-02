package com.busem.sample.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.common.DataState
import com.busem.data.models.Repository
import com.busem.data.repositories.DataSourceGithub
import com.busem.data.repositories.GithubRepo
import com.busem.data.common.DataException
import com.busem.sample.common.BaseViewModel
import com.busem.sample.common.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    private val githubRepo:DataSourceGithub = GithubRepo()
) : BaseViewModel() {

    private val _searchTerm by lazy { SingleLiveEvent<String>() }

    private val _repos by lazy { MutableLiveData<List<Repository>>() }
    val repos: LiveData<List<Repository>> by lazy { _repos }

    init {
        searchResults("Top")
    }

    fun searchResults(searchTerm: String) {
        _searchTerm.postValue(searchTerm)

        ioScope.launch {
            doWhileLoading {
                when (val dataState = githubRepo.fetchRepositories(searchTerm)) {
                    is DataState.Success -> _repos.postValue(dataState.data)
                    is DataState.Error -> {
                        when (dataState.dataException) {
                            is DataException.UnauthorizedException -> _repos.postValue(
                                githubRepo.getRepositories(searchTerm)
                            )
                            is DataException.ApiException -> _repos.postValue(
                                githubRepo.getRepositories(searchTerm)
                            )
                            is DataException.SocketTimeoutException -> _repos.postValue(
                                githubRepo.getRepositories(searchTerm)
                            )
                        }
                        dataState.logDetails()
                    }
                }
            }
        }
    }

    fun toggleFavorite(data: Repository) {
        ioScope.launch {
            githubRepo.saveRepository(data)
            _repos.postValue(githubRepo.getRepositories(_searchTerm.value ?: ""))
        }
    }
}