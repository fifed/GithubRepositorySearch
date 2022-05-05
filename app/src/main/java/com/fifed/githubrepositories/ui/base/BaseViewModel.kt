package com.fifed.githubrepositories.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.fifed.githubrepositories.concurrency.AppThreadExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel() {

    protected val viewModelScope = CoroutineScope(SupervisorJob() + AppThreadExecutor.dispatcher)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}