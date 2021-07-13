package com.busem.data.sharedPrefs

import com.busem.data.local.CacheProvider
import com.busem.data.models.Repository
import com.busem.data.models.User

class SharedPreferencesDataSourceImpl : SharedPreferencesDataSource {

    private val mSpHelper: SharedPreferenceHelper by lazy { SharedPreferenceHelper.getInstance() }
    override fun saveUser(user: User) = mSpHelper.putObject(SP_USER, user)

    override fun getUser(): User? = mSpHelper.getObject(SP_USER)

    override fun clearUser() =mSpHelper.remove(SP_USER)

    override fun deleteAllPrefs() = mSpHelper.clear()

    companion object {

        private const val SP_USER: String = "SP_LOGGED_IN_USER"
    }

}