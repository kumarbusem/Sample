package com.busem.sample.data.local.dataSources

import com.busem.sample.data.models.Repository

interface LocalGitDataSource {

    fun saveRepositories(repositories: List<Repository>)

    fun saveRepository(repo: Repository)

    fun getRepositories(searchKey: String): List<Repository>

    fun getRepository(id: Int) : Repository?
}