package com.busem.data.common

import com.busem.data.util.DataException
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {

        val response = call.invoke()
        if (response.isSuccessful) return response.body()

        when (response.code()) {
            401 -> throw UnauthorizedException()
            403 -> throw ApiException("Resource Forbidden - ${response.message()}")
            404 -> throw ApiException("Resource NotFound - ${response.message()}")
            500 -> throw ApiException("Internal Server Error - ${response.message()}")
            502 -> throw ApiException("Bad GateWay - ${response.message()}")
            301 -> throw ApiException("Resource Removed - ${response.message()}")
            302 -> throw ApiException("Removed Resource Found - ${response.message()}")
            else -> throw ApiException("Unknown Error Code - ${response.message()}")
        }

    }
}