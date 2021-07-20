package com.busem.data.common

import java.io.IOException

class ApiException(message: String) : IOException(message)
class UnauthorizedException() : IOException()