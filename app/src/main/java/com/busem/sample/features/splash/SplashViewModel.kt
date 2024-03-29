package com.busem.sample.features.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.repositories.DataSourceUserRepo
import com.busem.data.repositories.UserRepo
import com.busem.sample.common.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(
    private val userRepo: DataSourceUserRepo = UserRepo()
)  : BaseViewModel() {

    private val _navigateTo by lazy { MutableLiveData<NavDestination>() }
    val navigateTo: LiveData<NavDestination> by lazy { _navigateTo }

    fun checkAndNavigate() {
        ioScope.launch {
            delay(PURPOSEFUL_DELAY_MILLIS)
            // FIXME: Just a dummy check for user to be available, for splash navigation.
            //      User can be later authenticated and navigated accordingly.
            _navigateTo.postValue(
                if (userRepo.getUsers().isEmpty()) {
                    NavDestination.LOGIN
                } else {
                    NavDestination.HOME
                }
            )
        }
    }

    companion object {
        private const val PURPOSEFUL_DELAY_MILLIS = 1_000L
    }
}

enum class NavDestination {
    LOGIN,
    HOME
}