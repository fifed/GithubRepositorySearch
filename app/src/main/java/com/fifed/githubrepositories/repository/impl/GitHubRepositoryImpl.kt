package com.fifed.githubrepositories.repository.impl

import com.fifed.githubrepositories.function.executeCatching
import com.fifed.githubrepositories.mapper.PaginationResultMapper
import com.fifed.githubrepositories.mapper.RepositoriesAPIMapper
import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.network.RepositoriesApi
import com.fifed.githubrepositories.pagination.PaginationIntent
import com.fifed.githubrepositories.pagination.PaginationResult
import com.fifed.githubrepositories.repository.GitHubRepository

class GitHubRepositoryImpl(
    private val repositoriesApi: RepositoriesApi,
    private val apiMapper: RepositoriesAPIMapper,
    private val paginationMapper: PaginationResultMapper
) : GitHubRepository {

    override suspend fun searchRepositories(searchQuery: String, intent: PaginationIntent):
            Result<PaginationResult<List<RepositoryItemModel>>> {
        return executeCatching {
            repositoriesApi.searchRepositories(searchQuery, intent.currentPage, intent.pageSize).let { response ->
                paginationMapper.map(apiMapper.map(response), intent.currentPage) {
                    response.repositories.isEmpty()
                }
            }
        }
    }

    override suspend fun getRepository(ownerName: String, repositoryName: String): Result<RepositoryItemModel> {
        return executeCatching {
            apiMapper.map(repositoriesApi.getRepository(ownerName, repositoryName))
        }
    }
}