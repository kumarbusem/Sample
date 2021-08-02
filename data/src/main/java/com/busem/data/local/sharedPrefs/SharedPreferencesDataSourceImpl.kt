package com.busem.data.local.sharedPrefs

import com.busem.data.models.User

class SharedPreferencesDataSourceImpl : SharedPreferencesDataSource {

    private val mSpHelper: SharedPreferencesHelper by lazy { SharedPreferencesHelper.getInstance() }
    override fun saveUser(user: User) = mSpHelper.putObject(SP_USER, user)

    override fun getUser(): User? = mSpHelper.getObject(SP_USER)

    override fun clearUser() =mSpHelper.remove(SP_USER)

    override fun deleteAllPrefs() = mSpHelper.clear()

    companion object {
        private const val SP_USER: String = "SP_LOGGED_IN_USER"
    }

}