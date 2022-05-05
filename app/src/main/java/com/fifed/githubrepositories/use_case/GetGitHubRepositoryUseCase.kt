package com.fifed.githubrepositories.use_case

import com.fifed.githubrepositories.model.common.RepositoryItemModel

interface GetGitHubRepositoryUseCase {
    suspend fun execute(ownerName: String, repositoryName: String): Result<RepositoryItemModel>
}