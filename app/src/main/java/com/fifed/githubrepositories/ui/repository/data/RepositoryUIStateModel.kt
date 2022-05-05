package com.fifed.githubrepositories.ui.repository.data

data class RepositoryUIStateModel(
    val data: RepositoryUIModel = EmptyRepositoryUIModel,
    val showError: Boolean = false,
    val showProgress: Boolean = false
)

sealed interface RepositoryUIModel

data class RepositoryUIModelData(
    val name: String,
    val type: String,
    val ownerName: String,
    val ownerAvatar: String,
    val showLanguage: Boolean,
    val language: String,
    val showLicence: Boolean,
    val licenceName: String,
    val url: String,
    val defaultBranch: String,
    val forks: String,
    val issues: String
) : RepositoryUIModel

object EmptyRepositoryUIModel : RepositoryUIModel
