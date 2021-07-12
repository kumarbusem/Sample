package com.busem.sample.data.local

import androidx.room.Room
import com.busem.sample.SampleApp

/**
 * Singleton to instantiate and provide the local cache handling object.
 */
object CacheProvider {

    private const val DATABASE_NAME = "gitorade_db"

    /**
     * Lazily provides the [GithubDatabase] object that helps in maintaining the local cache.
     */
    val setup: GithubDatabase by lazy {

        Room.databaseBuilder(
            SampleApp.getAppContext(),
            GithubDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}