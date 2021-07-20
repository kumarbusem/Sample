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

    suspend fun fetchRepositories(searchKey: String): DataState<List<Repository>?>? {

        return try {
            val repositoriesResponseBody = remote.getRepositories(searchKey)

            if(repositoriesResponseBody != null){
                cache.saveRepositories(mapFromRemoteList(repositoriesResponseBody.repositories))
                DataState.Success(cache.getRepositories(searchKey))
            }else{
                DataState.InvalidData("Data Not Available")
            }

        } catch(e: UnauthorizedException){
            DataState.UnauthorizedException("Please Login Again")
        } catch(e: SocketTimeoutException){
            DataState.NetworkException("Slow Network")
        } catch(e: ApiException) {
            DataState.ApiError(e.message!!)
        } catch (e: Exception){
            DataState.Error(e.cause!!, e.message)
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