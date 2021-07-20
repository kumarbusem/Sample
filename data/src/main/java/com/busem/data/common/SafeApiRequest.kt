package com.busem.data.common

import android.util.Log
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): DataState<T?> {
        try{
            val response = call.invoke()
            if (response.isSuccessful) return DataState.success(response.body())

            return when(response.code()){
                401 -> DataState.UnauthorizedException("Unauthorized")
                403 -> DataState.ApiError("Resource Forbidden - ${response.message()}")
                404 -> DataState.ApiError("Resource NotFound - ${response.message()}")
                500 -> DataState.ApiError("Internal Server Error - ${response.message()}")
                502 -> DataState.ApiError("Bad GateWay - ${response.message()}")
                301 -> DataState.ApiError("Resource Removed - ${response.message()}")
                302 -> DataState.ApiError("Removed Resource Found - ${response.message()}")
                else -> DataState.ApiError("Unknown Error Code - ${response.message()}")
            }
        }catch (e: SocketTimeoutException){
            return DataState.NetworkException("Slow Network")
        }catch (e: Exception){
            return DataState.error(e.cause!!, e.message)
        }
    }
}