package com.busem.sample.data.local.dataSources

import com.busem.sample.data.models.User

interface UserDataSource {

    suspend fun saveUser(username: String, password: String)

    suspend fun getUser(username: String, password: String): User?

    suspend fun getUsers(): List<User>
}