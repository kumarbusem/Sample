package com.busem.sample.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.sample.common.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {


    private val _userType by lazy { MutableLiveData<UserType>() }
    val userType: LiveData<UserType> by lazy { _userType }

    fun gitInUser(username: String, password: String) {
        ioScope.launch {
            userRepo.getUser(username, password)?.let {
                _userType.postValue(UserType.EXISTING)
                return@let
            } ?: run {
                userRepo.saveUser(username, password)
                _userType.postValue(UserType.NEW)
                return@run
            }
        }
    }
}

enum class UserType {
    NEW,
    EXISTING
}