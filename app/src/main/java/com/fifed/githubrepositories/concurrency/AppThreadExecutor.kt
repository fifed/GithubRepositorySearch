package com.fifed.githubrepositories.concurrency

import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppThreadExecutor {
    private val executorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val dispatcher = executorService.asCoroutineDispatcher()
}