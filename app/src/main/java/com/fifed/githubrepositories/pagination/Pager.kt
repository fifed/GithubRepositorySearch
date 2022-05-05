package com.fifed.githubrepositories.pagination

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

interface Pager {
    fun reset()
    suspend fun <T> get(call: suspend (Int) ->  Result<PaginationResult<T>>):  Result<PaginationResult<T>>
}

class PagerImpl : Pager {

    private var currentPage = AtomicInteger(0)
    private var isLoading = AtomicBoolean()
    private var allDataReceived = AtomicBoolean()

    override fun reset() {
        currentPage.set(0)
        isLoading.compareAndSet(true, false)
        allDataReceived.compareAndSet(true, false)
    }

    override suspend fun <T> get(call: suspend (Int) ->  Result<PaginationResult<T>>): Result<PaginationResult<T>> {
        if(isLoading.get()) throw LoadingInProgressException()
        if(allDataReceived.get()) throw AllDataAlreadyReceivedException()
        isLoading.compareAndSet(false, true)
        val result = call(currentPage.get().inc())
        if(result.isSuccess) {
            currentPage.set(result.getOrThrow().currentPage)
            allDataReceived.compareAndSet(false, result.getOrThrow().lastPage)
        }
        isLoading.compareAndSet(true, false)
        return result
    }
}
class LoadingInProgressException: IllegalStateException("Request is executing, wait until response received")
class AllDataAlreadyReceivedException: IllegalStateException("All pages ale loaded, to reload all data reset pager")

