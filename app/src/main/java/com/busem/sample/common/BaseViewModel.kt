package com.busem.sample.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busem.data.repositories.GithubRepo
import com.busem.data.repositories.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * This class provides a [CoroutineScope] that dispatches on the [Dispatchers.Main] thread
 * and runs a [SupervisorJob] that gets cancelled when the [BaseViewModel] class gets cleared.
 */

abstract class BaseViewModel : ViewModel() {

    val obsIsDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val obsToastMessage by lazy { SingleLiveEvent<String>() }

    /*
    SupervisorJob that handles each task as a separate child.
     */
    private val job = SupervisorJob()

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.Main] thread by default.
     */
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.IO] thread by default.
     */
    val ioScope = CoroutineScope(job + Dispatchers.IO)

    protected val userRepo by lazy { UserRepo() }
    protected val githubRepo by lazy { GithubRepo() }

    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}