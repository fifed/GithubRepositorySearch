package com.fifed.githubrepositories.ui.repository.data.mapper.impl

import com.fifed.githubrepositories.R
import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.ui.repository.data.RepositoryUIModelData
import com.fifed.githubrepositories.ui.repository.data.mapper.RepositoryUIMapper
import com.fifed.githubrepositories.ui.resource.ResourceManager

class RepositoryUIMapperImpl(
    private val resourceManager: ResourceManager
) : RepositoryUIMapper {
    override fun map(model: RepositoryItemModel): RepositoryUIModelData {
        return with(model) {
            RepositoryUIModelData(
                name, getTypeText(isPrivate),
                owner.login,
                owner.avatarUrl,
                language != null,
                language.orEmpty(),
                license != null,
                license?.name.orEmpty(),
                url,
                defaultBranch,
                forks.toString(),
                issues.toString()
            )
        }
    }

    private fun getTypeText(isPrivate: Boolean): String {
        return resourceManager.getString(if (isPrivate) R.string._private else R.string._public)
    }

}