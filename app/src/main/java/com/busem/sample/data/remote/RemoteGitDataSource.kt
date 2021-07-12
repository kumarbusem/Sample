package com.busem.sample.data.remote

import com.busem.sample.data.models.RemoteRepository


interface RemoteGitDataSource {

    fun getRepositories(searchKey: String, callback: RepositoryResponse)

    interface RepositoryResponse {
        fun onSuccess(repos: List<RemoteRepository>)
        fun onFailure()
    }
}