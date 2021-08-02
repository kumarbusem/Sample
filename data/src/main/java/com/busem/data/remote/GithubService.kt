package com.busem.data.remote

import com.busem.data.common.QUERY_PARAMETER_KEY
import com.busem.data.models.RemoteRepository
import com.busem.data.models.Repository
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Remote service to fetch for the repositories list.
 */
interface GithubService {

    @GET("search/repositories")
    suspend fun fetchRepositories(
        @Query(QUERY_PARAMETER_KEY) q: String
    ): Response<RepositoriesResponseBody>
}


data class RepositoriesResponseBody(
    @SerializedName(TOTAL_COUNT) val totalCount: Int,
    @SerializedName(INCOMPLETE_RESULTS) val incompleteResults: Boolean,
    @SerializedName(ITEMS) val repositories: List<RemoteRepository>
) {

    companion object {
        const val TOTAL_COUNT = "total_count"
        const val INCOMPLETE_RESULTS = "incomplete_results"
        const val ITEMS = "items"
    }
}