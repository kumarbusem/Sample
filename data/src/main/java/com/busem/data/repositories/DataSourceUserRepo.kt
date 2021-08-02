package com.busem.data.repositories

import com.busem.data.models.User

interface DataSourceUserRepo {

    suspend fun saveUser(username: String, password: String)

    suspend fun getUser(username: String, password: String) : User?

    suspend fun getUsers(): List<User>
}