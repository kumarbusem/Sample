package com.busem.data.repositories

import android.provider.ContactsContract
import android.service.autofill.Dataset
import com.busem.data.common.ApiException
import com.busem.data.common.DataState
import com.busem.data.common.EMPTY_STRING
import com.busem.data.common.UnauthorizedException
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.RemoteRepository
import com.busem.data.models.Repository
import com.busem.data.remote.RetrofitDataSourceImpl
import java.lang.Exception
import java.net.SocketTimeoutException

class GithubRepo {

    private val cache = LocalGitDataSourceImpl()
    private val remote = RetrofitDataSourceImpl()

    suspend fun fetchRepositories(searchKey: String): DataState<List<Repository>?> {

        return when(val result = remote.getRepositories(searchKey)){
            is DataState.Success -> {
                cache.saveRepositories(mapFromRemoteList(result.data?.repositories!!))
                DataState.Success(cache.getRepositories(searchKey))
            }
            is DataState.ApiError -> DataState.ApiError(result.message)
            is DataState.Error -> DataState.Error(result.throwable, result.errorMessage)
            is DataState.InvalidData -> DataState.InvalidData(result.message)
            is DataState.NetworkException -> DataState.NetworkException(result.message)
            is DataState.UnauthorizedException -> DataState.UnauthorizedException(result.message)
        }
    }

    fun getRepositories(searchKey: String): List<Repository> =
        cache.getRepositories(searchKey)

    fun saveRepository(repo: Repository) = cache.saveRepository(repo)

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