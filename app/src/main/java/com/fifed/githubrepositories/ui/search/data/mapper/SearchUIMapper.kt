package com.fifed.githubrepositories.ui.search.data.mapper

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.ui.search.data.RepositoryItemUIModel

interface SearchUIMapper {
    fun map(data: List<RepositoryItemModel>): List<RepositoryItemUIModel>
}