package com.fifed.githubrepositories.ui.repository.data.mapper

import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.ui.repository.data.RepositoryUIModelData

interface RepositoryUIMapper {
    fun map(model: RepositoryItemModel): RepositoryUIModelData
}