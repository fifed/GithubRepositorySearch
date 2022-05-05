package com.fifed.githubrepositories.mapper.impl

import com.fifed.githubrepositories.mapper.RepositoriesAPIMapper
import com.fifed.githubrepositories.model.api_model.responses.LicenseApiModel
import com.fifed.githubrepositories.model.api_model.responses.OwnerApiModel
import com.fifed.githubrepositories.model.api_model.responses.RepositoryApiModel
import com.fifed.githubrepositories.model.api_model.responses.SearchRepositoriesResponse
import com.fifed.githubrepositories.model.common.RepositoryItemModel
import com.fifed.githubrepositories.model.common.RepositoryLicenseModel
import com.fifed.githubrepositories.model.common.RepositoryOwnerModel

class RepositoriesAPIMapperImpl : RepositoriesAPIMapper {
    override fun map(response: SearchRepositoriesResponse): List<RepositoryItemModel> {
        return response.repositories.map { item ->
            map(item)
        }
    }

    override fun map(model: RepositoryApiModel): RepositoryItemModel {
        return with(model) {
            RepositoryItemModel(id, mapLicense(license), name, mapOwner(owner), private, language, htmlUrl, defaultBranch, forks, openIssues)
        }
    }

    private fun mapLicense(licence: LicenseApiModel?): RepositoryLicenseModel? {
        if (licence == null) return null
        return licence.run { RepositoryLicenseModel(key, name, nodeId, spdxId, url) }
    }

    private fun mapOwner(owner: OwnerApiModel): RepositoryOwnerModel {
        return owner.run {
            RepositoryOwnerModel(
                avatarUrl, eventsUrl, followersUrl, followingUrl, gistsUrl, gravatarId, htmlUrl, id, login, nodeId,
                organizationsUrl, receivedEventsUrl, reposUrl, siteAdmin, starredUrl, subscriptionsUrl, type, url
            )
        }
    }
}