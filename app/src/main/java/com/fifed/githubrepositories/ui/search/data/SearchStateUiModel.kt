package com.fifed.githubrepositories.ui.search.data

data class SearchStateUiModel(
    val searchText: String = "",
    val showCentralProgress: Boolean = false,
    val showCentralError: Boolean = false,
    val showNoResults: Boolean = false,
    val showDefaultPlaceHolder: Boolean = true,
    val showBottomProgress: Boolean = false,
    val showBottomError: Boolean = false,
    val items: List<RepositoryItemUIModel> = emptyList()
)
