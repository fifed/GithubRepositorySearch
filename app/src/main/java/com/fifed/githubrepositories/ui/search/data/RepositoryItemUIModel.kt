package com.fifed.githubrepositories.ui.search.data

import androidx.annotation.ColorInt

data class RepositoryItemUIModel(
    val id: Int,
    val name: String,
    val type: String,
    @ColorInt val typeColor: Int,
    val ownerName: String,
    val ownerAvatar: String
)

