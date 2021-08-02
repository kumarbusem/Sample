package com.busem.data.remote

import android.content.Context
import androidx.annotation.Keep
import com.busem.data.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Singleton remote service provider that provides the network call handling object.
 */
object ServiceProvider {

    /**
     * Builds and provides the [Retrofit] object lazily.
     *
     * This object is required to make remote service calls and fetch the required
     * data from the internet.
     */

    private var setup: Retrofit? = null

    fun initialize() {
        if (setup == null) {
            setup =  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
        }
    }

    fun getInstance(): Retrofit {
        checkNotNull(setup) { "SharedPreferences not initialized" }
        return setup!!
    }


}