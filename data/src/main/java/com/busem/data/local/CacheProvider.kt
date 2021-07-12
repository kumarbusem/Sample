package com.busem.data.local

import android.content.Context
import androidx.room.Room

/**
 * Singleton to instantiate and provide the local cache handling object.
 */
object CacheProvider {

    private const val DATABASE_NAME = "busem_db"

    private var setup: GithubDatabase? = null

    fun initialize(context: Context) {
        if (setup == null) {
            setup = Room.databaseBuilder(
                context,
                GithubDatabase::class.java,
                CacheProvider.DATABASE_NAME
            ).build()
        }
    }

    fun getInstance(): GithubDatabase {
        checkNotNull(setup) { "SharedPreferences not initialized" }
        return setup!!
    }

}