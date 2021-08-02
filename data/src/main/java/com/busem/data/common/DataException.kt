package com.busem.data.common

import java.io.IOException

sealed class DataException: IOException() {

    abstract override val message: String

    class UnauthorizedException(override val message: String) : DataException()
    class SocketTimeoutException(override val message: String) : DataException()
    class ApiException(override val message: String) : DataException()
}
