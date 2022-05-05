package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.repository.GitHubRepository
import com.fifed.githubrepositories.repository.impl.GitHubRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    factory<GitHubRepository> { GitHubRepositoryImpl(get(), get(), get()) }
}