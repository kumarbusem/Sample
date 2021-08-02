package com.busem.data.remote

import com.busem.data.common.SafeApiRequest

class RemoteGitDataSourceImpl : RemoteGitDataSource, SafeApiRequest() {

    private val service = ServiceProvider.getInstance().create(GithubService::class.java)

    override suspend fun fetchRepositories(searchKey: String): RepositoriesResponseBody? {
        return apiRequest { service.fetchRepositories(searchKey) }
    }


}