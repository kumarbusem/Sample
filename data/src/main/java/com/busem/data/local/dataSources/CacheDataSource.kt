package com.busem.data.local.dataSources

import com.busem.data.local.CacheProvider
import com.busem.data.models.Repository

class CacheDataSource : LocalGitDataSource {

    private val cache = CacheProvider.getInstance().githubDao()

    override fun saveRepositories(repositories: List<Repository>) {
        cache.saveRepos(repositories)
    }

    override fun getRepositories(searchKey: String): List<Repository> {
        return cache.getRepos("%$searchKey%")
    }

    override fun saveRepository(repo: Repository) {
        cache.saveRepo(repo)
    }

    override fun getRepository(id: Int): Repository? {
        return cache.getRepo(id)
    }

}