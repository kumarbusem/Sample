package com.busem.data.util

sealed class DataException {

    abstract val message: String

    class UnauthorizedException(override val message: String) : DataException()
    class SocketTimeoutException(override val message: String) : DataException()
    class ApiException(override val message: String) : DataException()
}
