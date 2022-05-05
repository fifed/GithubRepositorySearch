package com.fifed.githubrepositories.ui.repository

import com.fifed.githubrepositories.ui.base.BaseViewModel
import com.fifed.githubrepositories.ui.repository.data.RepositoryUIStateModel
import com.fifed.githubrepositories.ui.repository.data.mapper.RepositoryUIMapper
import com.fifed.githubrepositories.use_case.GetGitHubRepositoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class RepositoryViewModel : BaseViewModel() {
    abstract val uiFlow: Flow<RepositoryUIStateModel>
    abstract fun load(ownerName: String, repositoryName: String)
}

class RepositoryViewModelImpl(
    private val getGitHubRepositoryUseCase: GetGitHubRepositoryUseCase,
    private val repositoryUIMapper: RepositoryUIMapper
) : RepositoryViewModel() {

    override val uiFlow: MutableStateFlow<RepositoryUIStateModel> = MutableStateFlow(RepositoryUIStateModel())

    override fun load(ownerName: String, repositoryName: String) {
        viewModelScope.launch {
            uiFlow.emit(uiFlow.value.copy(showError = false, showProgress = true))
            val result = getGitHubRepositoryUseCase.execute(ownerName, repositoryName)
            if (result.isSuccess) {
                val data = repositoryUIMapper.map(result.getOrThrow())
                uiFlow.emit(uiFlow.value.copy(data = data, showProgress = false))
            } else {
                uiFlow.emit(uiFlow.value.copy(showError = true, showProgress = false))
            }
        }
    }
}