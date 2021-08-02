package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.common.EMPTY_STRING
import com.busem.data.local.dataSources.LocalGitDataSource
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.RemoteRepository
import com.busem.data.models.Repository
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class GithubRepo : DataSourceGithub {

    private val cache: LocalGitDataSource by lazy { LocalGitDataSourceImpl() }
    private val remote: RemoteGitDataSource by lazy { RemoteGitDataSourceImpl() }

    override suspend fun fetchRepositories(searchKey: String):
            DataState<List<Repository>?> = DataState.asDataState {

        val repositoriesResponseBody = remote.fetchRepositories(searchKey)

        if (repositoriesResponseBody != null) {
            val mapped = mapFromRemoteList(repositoriesResponseBody.repositories)
            cache.saveRepositories(mapped)
        }

        cache.getRepositories(searchKey)
    }

    override fun getRepositories(searchKey: String): List<Repository> =
        cache.getRepositories(searchKey)

    override fun saveRepository(repo: Repository) = cache.saveRepository(repo)

    private fun mapFromRemoteToEntity(repo: RemoteRepository): Repository {
        return Repository(
            id = repo.id,
            name = repo.name ?: EMPTY_STRING,
            ownerImage = repo.owner?.profilePicUrl ?: EMPTY_STRING,
            description = repo.description ?: EMPTY_STRING,
            fullName = repo.fullName ?: EMPTY_STRING,
            watchersCount = repo.watchersCount ?: 0,
            htmlUrl = repo.htmlUrl ?: EMPTY_STRING,
            hasDownloads = repo.hasDownloads ?: false,
            hasProjects = repo.hasProjects ?: false,
            hasIssues = repo.hasIssues ?: false,
            hasWiki = repo.hasWiki ?: false,
            commitsUrl = repo.commitsUrl ?: EMPTY_STRING,
            contributorsUrl = repo.contributorsUrl ?: EMPTY_STRING,
            isFavorite = cache.getRepository(repo.id)?.isFavorite ?: false
        )
    }

    private fun mapFromRemoteList(remoteEntities: List<RemoteRepository>): List<Repository> {
        return remoteEntities.map { mapFromRemoteToEntity(it) }
    }

}