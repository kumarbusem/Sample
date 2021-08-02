package com.busem.data.common

import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        try {
            val response = call.invoke()
            if (response.isSuccessful) return response.body()

            when (response.code()) {
                401 -> throw DataException.UnauthorizedException("jjj")
                403 -> throw DataException.ApiException("Resource Forbidden - ${response.message()}")
                404 -> throw DataException.ApiException("Resource NotFound - ${response.message()}")
                500 -> throw DataException.ApiException("Internal Server Error - ${response.message()}")
                502 -> throw DataException.ApiException("Bad GateWay - ${response.message()}")
                301 -> throw DataException.ApiException("Resource Removed - ${response.message()}")
                302 -> throw DataException.ApiException("Removed Resource Found - ${response.message()}")
                else -> throw DataException.ApiException("Unknown Error Code - ${response.message()}")
            }
        }catch (e: SocketTimeoutException){
            throw DataException.SocketTimeoutException("Slow Network")
        }catch (e: Exception){
            throw DataException.ApiException("Unknown Error - ${e.message.toString()}")
        }
    }
}