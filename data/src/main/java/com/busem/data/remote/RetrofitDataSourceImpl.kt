package com.busem.data.remote

import android.util.Log
import com.busem.data.common.DataState
import com.busem.data.models.RemoteRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class RetrofitDataSourceImpl : RemoteGitDataSource {

    private val service = ServiceProvider.getInstance().create(GithubService::class.java)

    override suspend fun getRepositories(searchKey: String): RepositoriesResponseBody?{

        return service.getRepositories(searchKey)

    }

}