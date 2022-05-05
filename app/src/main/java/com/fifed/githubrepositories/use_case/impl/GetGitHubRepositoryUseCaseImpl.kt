package com.fifed.githubrepositories.use_case.impl

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.repository.GitHubRepository
import com.fifed.githubrepositories.use_case.GetGitHubRepositoryUseCase

class GetGitHubRepositoryUseCaseImpl(private val repository: GitHubRepository) : GetGitHubRepositoryUseCase {
    override suspend fun execute(ownerName: String, repositoryName: String): Result<RepositoryItemModel> {
        return repository.getRepository(ownerName, repositoryName)
    }
}