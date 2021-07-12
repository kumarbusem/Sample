package com.busem.data.local.dataSources

import com.busem.data.local.CacheProvider
import com.busem.data.models.User

class UserDataSourceImpl : UserDataSource {

    private val cache by lazy { CacheProvider.getInstance().userDao() }

    override suspend fun saveUser(username: String, password: String) {
        cache.saveUser(User(username, password))
    }

    override suspend fun getUser(username: String, password: String): User? {
        return cache.getUser(username, password)
    }

    override suspend fun getUsers(): List<User> {
        return cache.getUsers()
    }
}