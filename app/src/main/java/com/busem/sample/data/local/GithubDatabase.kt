package com.busem.sample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.busem.sample.data.local.dao.GitHubDao
import com.busem.sample.data.local.dao.UserDao
import com.busem.sample.data.models.Repository
import com.busem.sample.data.models.User

/**
 * [RoomDatabase] to handle the C.R.U.D. operations through the respective Data Access Objects.
 */
@Database(entities = [Repository::class, User::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubDao(): GitHubDao

    abstract fun userDao(): UserDao
}