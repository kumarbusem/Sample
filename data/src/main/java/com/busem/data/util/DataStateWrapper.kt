package com.busem.data.util

import com.busem.data.common.DataState

@Suppress("RedundantSuspendModifier")
suspend inline fun <T> wrapAsDataState(logic: () -> T): DataState<T> {

    val result = runCatching { logic() }

    return result
        .exceptionOrNull()
        ?.let { throwable -> DataState.error(throwable) }
        ?: run { DataState.success(result.getOrThrow()) }
}