package com.busem.data.local.dataSources

import com.busem.data.models.Repository

interface LocalGitDataSource {

    fun saveRepositories(repositories: List<Repository>)

    fun saveRepository(repo: Repository)

    fun getRepositories(searchKey: String): List<Repository>

    fun getRepository(id: Int) : Repository?
}