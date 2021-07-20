package com.busem.sample.features.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.common.DataState
import com.busem.data.models.Repository
import com.busem.sample.common.BaseViewModel
import com.busem.sample.common.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel(){

    private val _searchTerm by lazy { SingleLiveEvent<String>() }

    private val _repos by lazy { MutableLiveData<List<Repository>>() }
    val repos: LiveData<List<Repository>> by lazy { _repos }

    init {
        searchResults("Top")
    }

    fun searchResults(searchTerm: String) {
        _searchTerm.postValue(searchTerm)

        ioScope.launch {
            when (val dataState = githubRepo.fetchRepositories(searchTerm)) {
                is DataState.Success -> _repos.postValue(dataState.data)
                is DataState.Error -> _repos.postValue(githubRepo.getRepositories(searchTerm))
                is DataState.ApiError -> _repos.postValue(githubRepo.getRepositories(searchTerm))
                is DataState.NetworkException -> _repos.postValue(githubRepo.getRepositories(searchTerm))
                is DataState.UnauthorizedException -> _repos.postValue(githubRepo.getRepositories(searchTerm))
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