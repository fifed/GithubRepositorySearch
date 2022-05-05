package com.fifed.githubrepositories.ui.repository

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

data class RepositoryScreenStarter(private val ownerName: String, private val repositoryName: String) {

    fun getRoute(): String {
        return "${this.javaClass.name}?$OWNER=$ownerName&$REPOSITORY=$repositoryName"
    }

    companion object {
        private const val OWNER = "owner"
        private const val REPOSITORY = "repository"
        val graphRoute = "${RepositoryScreenStarter::class.java.name}?$OWNER={$OWNER}&$REPOSITORY={$REPOSITORY}"
        fun getOwnerName(arguments: Bundle) = requireNotNull(arguments.getString(OWNER))
        fun getRepositoryName(arguments: Bundle) = requireNotNull(arguments.getString(REPOSITORY))
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(OWNER) {
                type = NavType.StringType
            }, navArgument(REPOSITORY) {
                type = NavType.StringType
            }
        )
    }
}
