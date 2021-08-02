package com.busem.data.common

public sealed class DataState<T> {

    data class Error<T> internal constructor(
        val dataException: DataException,
        val errorMessage: String?,
    ) : DataState<T>()

    data class Success<T> internal constructor(
        val data: T
    ) : DataState<T>()


    fun getOrNull(): T? = when (this) {
        is Error -> null
        is Success -> data
    }

    fun getOrThrow(): T = when (this) {
        is Error -> throw dataException
        is Success -> data
    }

    fun exceptionOrNull(): DataException? = when (this) {
        is Error -> dataException
        is Success -> null
    }

    override fun toString(): String = when (this) {
        is Error -> "QueryResult.Error(errorMessage:${errorMessage}, throwable:${dataException})"
        is Success -> "QueryResult.Success(data:${data.toString()})"
    }

    @PublishedApi
    internal companion object {

        fun <T> success(data: T): Success<T> = Success(data = data)

        fun <T> error(
            throwable: DataException,
            message: String? = null,
        ): Error<T> = Error(dataException = throwable, errorMessage = message)

        suspend inline fun <T> asDataState(
            crossinline logic: suspend () -> T
        ): DataState<T> {

            val result = kotlin.runCatching { logic() }

            return result.exceptionOrNull()
                ?.let { ex -> error(throwable = ex as DataException) }
                ?: run { success(result.getOrThrow()) }
        }
    }
}