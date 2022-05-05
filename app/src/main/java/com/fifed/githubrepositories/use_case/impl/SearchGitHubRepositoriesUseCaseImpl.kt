package com.fifed.githubrepositories.use_case.impl

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.pagination.Pager
import com.fifed.githubrepositories.pagination.PaginationIntent
import com.fifed.githubrepositories.pagination.PaginationResult
import com.fifed.githubrepositories.repository.GitHubRepository
import com.fifed.githubrepositories.use_case.SearchGitHubRepositoriesUseCase

private const val PAGE_SIZE = 10

class SearchGitHubRepositoriesUseCaseImpl(
    private val pager: Pager,
    private val repository: GitHubRepository
) : SearchGitHubRepositoriesUseCase {

    override suspend fun execute(searchQuery: String): Result<PaginationResult<List<RepositoryItemModel>>> {
        return pager.get { page ->
            repository.searchRepositories(searchQuery, PaginationIntent(PAGE_SIZE, page))
        }
    }

    override fun reset() {
        pager.reset()
    }

}