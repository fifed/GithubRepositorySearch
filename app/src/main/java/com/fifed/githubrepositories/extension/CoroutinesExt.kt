package com.fifed.githubrepositories.extension

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> MutableStateFlow<T>.reEmit(block: T.() -> T) {
    emit(block(value))
}

inline fun onError(crossinline run: (Throwable) -> Unit): CoroutineExceptionHandler = CoroutineExceptionHandler { _, t -> run(t) }