package com.fifed.githubrepositories.model.common

data class RepositoryItemModel(
    val id: Int,
    val license: RepositoryLicenseModel?,
    val name: String,
    val owner: RepositoryOwnerModel,
    val isPrivate: Boolean,
    val language: String?,
    val url: String,
    val defaultBranch: String,
    val forks: Int,
    val issues: Int
)