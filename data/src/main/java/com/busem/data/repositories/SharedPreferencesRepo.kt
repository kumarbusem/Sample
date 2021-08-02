package com.busem.data.repositories

import com.busem.data.models.User
import com.busem.data.local.sharedPrefs.SharedPreferencesDataSource
import com.busem.data.local.sharedPrefs.SharedPreferencesDataSourceImpl

class SharedPreferencesRepo: SharedPreferencesDataSource {

    private val mSpDS: SharedPreferencesDataSource by lazy { SharedPreferencesDataSourceImpl() }

    override fun saveUser(user: User) = mSpDS.saveUser(user)

    override fun getUser(): User? = mSpDS.getUser()

    override fun clearUser() = mSpDS.clearUser()

    override fun deleteAllPrefs() = mSpDS.deleteAllPrefs()
}