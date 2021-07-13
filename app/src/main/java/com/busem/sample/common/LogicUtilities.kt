package com.busem.sample.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.net.MalformedURLException
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Function to verify whether a provided [url] is valid or not.
 *
 * Return a [Boolean] respectively based on the result.
 */
fun String.isValidUrl(): Boolean {
    return try {
        URL(this)
        true
    } catch (ex: MalformedURLException) {
        Log.e("LogicUtils", "The provided htmlUrl: \"$this\" is invalid.")
        false
    }
}

/**
 * Function to parse the provided [date] based on its [actualDateFormat] into [desiredDateFormat] and return
 * the result.
 */
@Throws(ParseException::class)
fun getDesiredDateInDesiredFormat(
    date: String,
    actualDateFormat: String,
    desiredDateFormat: String
): String {

    val simpleDateFormat = SimpleDateFormat(actualDateFormat, Locale.getDefault())

    if (actualDateFormat == UTC_DATE_FORMAT) {
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    val finalDate = simpleDateFormat.parse(date)

    val desiredDate = SimpleDateFormat(desiredDateFormat, Locale.getDefault())
    desiredDate.timeZone = TimeZone.getDefault()
    return desiredDate.format(finalDate)
}

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

