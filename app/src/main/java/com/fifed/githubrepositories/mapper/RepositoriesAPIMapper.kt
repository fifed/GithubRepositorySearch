package com.fifed.githubrepositories.mapper

import com.fifed.githubrepositories.model.api_model.responses.RepositoryApiModel
import com.fifed.githubrepositories.model.api_model.responses.SearchRepositoriesResponse
import com.fifed.githubrepositories.model.common.RepositoryItemModel

interface RepositoriesAPIMapper {
    fun map(response: SearchRepositoriesResponse): List<RepositoryItemModel>
    fun map(model: RepositoryApiModel): RepositoryItemModel
}