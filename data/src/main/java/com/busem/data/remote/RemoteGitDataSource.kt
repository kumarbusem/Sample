package com.busem.data.remote

import com.busem.data.common.DataState
import com.busem.data.models.RemoteRepository


interface RemoteGitDataSource {

    suspend fun getRepositories(searchKey: String): DataState<RepositoriesResponseBody?>

}