package com.fifed.githubrepositories.use_case

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.pagination.PaginationResult

interface SearchGitHubRepositoriesUseCase {
    suspend fun execute(searchQuery: String): Result<PaginationResult<List<RepositoryItemModel>>>
    fun reset()
}