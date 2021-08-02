package com.busem.data.local.sharedPrefs

import com.busem.data.models.User


interface SharedPreferencesDataSource {

    abstract fun saveUser(user: User)
    abstract fun getUser(): User?
    abstract fun clearUser()

    abstract fun deleteAllPrefs()
}