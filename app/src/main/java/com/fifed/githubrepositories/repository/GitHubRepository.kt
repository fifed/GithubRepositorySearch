package com.fifed.githubrepositories.repository

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.pagination.PaginationIntent
import com.fifed.githubrepositories.pagination.PaginationResult

interface GitHubRepository {
    suspend fun searchRepositories(searchQuery: String, intent: PaginationIntent):
            Result<PaginationResult<List<RepositoryItemModel>>>

    suspend fun getRepository(ownerName: String, repositoryName: String): Result<RepositoryItemModel>
}