package com.fifed.githubrepositories.ui.search

import android.util.Log
import com.fifed.githubrepositories.extension.onError
import com.fifed.githubrepositories.extension.reEmit
import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.pagination.PaginationResult
import com.fifed.githubrepositories.ui.base.BaseViewModel
import com.fifed.githubrepositories.ui.search.data.SearchStateUiModel
import com.fifed.githubrepositories.ui.search.data.mapper.SearchUIMapper
import com.fifed.githubrepositories.use_case.SearchGitHubRepositoriesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class SearchViewModel : BaseViewModel() {
    abstract val uiStateFlow: Flow<SearchStateUiModel>
    abstract fun init()
    abstract fun onSearchTextChanged(text: String)
    abstract fun handleScrollEnding()
    abstract fun retry()
}

class SearchViewModelImpl(
    private val searchUseCase: SearchGitHubRepositoriesUseCase,
    private val searchUIMapper: SearchUIMapper
) : SearchViewModel() {
    override val uiStateFlow: MutableStateFlow<SearchStateUiModel> = MutableStateFlow(SearchStateUiModel())
    private val loadedRepositories: MutableList<RepositoryItemModel> = ArrayList()

    override fun init() {
        subscribeOnTextChanges()
    }

    private fun subscribeOnTextChanges() {
        viewModelScope.launch {
            uiStateFlow.drop(1).map { it.searchText }.debounce(500).distinctUntilChanged()
                .collect { text ->
                    loadedRepositories.clear()
                    searchUseCase.reset()
                    load(text)
                }
        }
    }

    private fun load(text: String) {
        viewModelScope.launch(
            onError { Log.e(javaClass.simpleName, it.message, it) }
        ) {
            if (loadedRepositories.isEmpty()) {
                uiStateFlow.reEmit { copy(showCentralProgress = true, showNoResults = false, showDefaultPlaceHolder = false) }
            }
            if (text.isNotBlank()) {
                handleLoadResult(searchUseCase.execute(text))
            } else {
                uiStateFlow.emit(SearchStateUiModel())
            }
        }
    }

    override fun retry() {
        viewModelScope.launch {
            with(uiStateFlow.value) {
                if (showBottomProgress) {
                    uiStateFlow.emit(copy(showBottomProgress = true))
                } else {
                    uiStateFlow.emit(uiStateFlow.value.copy(showCentralError = false))
                }
                load(searchText)
            }
        }
    }

    private suspend fun handleLoadResult(result: Result<PaginationResult<List<RepositoryItemModel>>>) {
        uiStateFlow.reEmit { copy(showDefaultPlaceHolder = false) }
        if (result.isSuccess) {
            handleSuccess(result)
        } else {
            handleError()
        }
    }

    private suspend fun handleError() {
        with(uiStateFlow.value) {
            if (items.isNotEmpty()) {
                uiStateFlow.emit(copy(showBottomError = true, showBottomProgress = false))
            } else {
                uiStateFlow.emit(copy(showCentralError = true, showCentralProgress = false))
            }
        }
    }

    private suspend fun handleSuccess(result: Result<PaginationResult<List<RepositoryItemModel>>>) {
        loadedRepositories.addAll(result.getOrThrow().data)
        uiStateFlow.reEmit {
            copy(
                items = searchUIMapper.map(loadedRepositories),
                showBottomProgress = false, showCentralProgress = false,
                showNoResults = loadedRepositories.isEmpty()
            )
        }
    }

    override fun onSearchTextChanged(text: String) {
        viewModelScope.launch {
            with(uiStateFlow.value) {
                uiStateFlow.emit(copy(searchText = text))
            }
        }
    }

    override fun handleScrollEnding() {
        viewModelScope.launch {
            with(uiStateFlow.value) {
                if (!showBottomProgress && !showBottomError) {
                    uiStateFlow.emit(value = copy(showBottomProgress = true))
                    load(searchText)
                }
            }
        }
    }
}