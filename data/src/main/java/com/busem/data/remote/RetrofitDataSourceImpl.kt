package com.busem.data.remote

import com.busem.data.common.DataState
import com.busem.data.common.SafeApiRequest
import com.busem.data.models.Repository

class RetrofitDataSourceImpl : RemoteGitDataSource, SafeApiRequest() {

    private val service = ServiceProvider.getInstance().create(GithubService::class.java)

    override suspend fun getRepositories(searchKey: String): RepositoriesResponseBody? {
        return apiRequest { service.getRepositories(searchKey) }
    }


}