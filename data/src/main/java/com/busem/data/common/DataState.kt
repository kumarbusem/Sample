package com.busem.data.common

sealed class DataState<T> {

    data class Error<T> internal constructor(
        val throwable: Throwable,
        val errorMessage: String?,
    ) : DataState<T>()

    data class Success<T> internal constructor(
        val data: T
    ) : DataState<T>()

    data class Loading<T> internal constructor(
        val message: T
    ) : DataState<T>()

    fun getOrNull(): T? = when (this) {
        is Error -> null
        is Success -> data
        is Loading -> message
    }

    fun getOrThrow(): T = when (this) {
        is Error -> throw throwable
        is Success -> data
        is Loading -> message
    }

    fun exceptionOrNull(): Throwable? = when (this) {
        is Error -> throwable
        is Success -> null
        is Loading -> null
    }

    override fun toString(): String = when (this) {
        is Error -> "QueryResult.Error(errorMessage:${errorMessage}, throwable:${throwable})"
        is Success -> "QueryResult.Success(data:${data.toString()})"
        is Loading -> "QueryResult.Loading(message:${message.toString()})"
    }

    @PublishedApi
    internal companion object {

        fun <T> success(data: T): Success<T> = Success(data = data)

        fun <T> error(
            throwable: Throwable,
            message: String? = null,
        ): Error<T> = Error(throwable = throwable, errorMessage = message)

        suspend inline fun <T> asDataState(
            crossinline logic: suspend () -> T
        ): DataState<T> {

            val result = kotlin.runCatching { logic() }

            return result.exceptionOrNull()
                ?.let { ex -> error(throwable = ex) }
                ?: run { success(result.getOrThrow()) }
        }
    }
}