package com.fifed.githubrepositories.ui.search.data.mapper.impl

import com.fifed.githubrepositories.R
import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.ui.resource.ResourceManager
import com.fifed.githubrepositories.ui.search.data.RepositoryItemUIModel
import com.fifed.githubrepositories.ui.search.data.mapper.SearchUIMapper

class SearchUIMapperImpl(private val resourceManager: ResourceManager) : SearchUIMapper {
    override fun map(data: List<RepositoryItemModel>): List<RepositoryItemUIModel> {
        return data.map {
            RepositoryItemUIModel(
                it.id, it.name, getTypeText(it.isPrivate),
                getTypeColor(it.isPrivate), it.owner.login, it.owner.avatarUrl
            )
        }
    }

    private fun getTypeText(isPrivate: Boolean): String {
        return resourceManager.getString(if (isPrivate) R.string._private else R.string._public)
    }

    private fun getTypeColor(isPrivate: Boolean): Int {
        return resourceManager.getColor(if (isPrivate) R.color.teal_700 else R.color.purple_700)
    }
}