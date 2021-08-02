package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.models.Repository
import com.busem.data.models.User

interface DataSourceGithub {


    suspend fun fetchRepositories(searchKey: String): DataState<List<Repository>?>

    fun getRepositories(searchKey: String): List<Repository>

    fun saveRepository(repo: Repository)
}