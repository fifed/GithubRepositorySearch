package com.fifed.githubrepositories.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> executeCatching(context: CoroutineContext = Dispatchers.IO, call: suspend () -> T): Result<T> {
    return withContext(context) {
        runCatching { call() }
    }
}