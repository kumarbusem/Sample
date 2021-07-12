package com.busem.sample.data.repositories

import com.busem.sample.data.models.User

interface UserRepository {

    suspend fun saveUser(username: String, password: String)

    suspend fun getUser(username: String, password: String) : User?

    suspend fun getUsers(): List<User>
}