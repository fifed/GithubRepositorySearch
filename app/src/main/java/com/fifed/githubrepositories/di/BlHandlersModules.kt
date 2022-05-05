package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.pagination.Pager
import com.fifed.githubrepositories.pagination.PagerImpl
import com.fifed.githubrepositories.use_case.GetGitHubRepositoryUseCase
import com.fifed.githubrepositories.use_case.SearchGitHubRepositoriesUseCase
import com.fifed.githubrepositories.use_case.impl.GetGitHubRepositoryUseCaseImpl
import com.fifed.githubrepositories.use_case.impl.SearchGitHubRepositoriesUseCaseImpl
import org.koin.dsl.module

val blHandlersModules = module {
    factory<Pager> { PagerImpl() }
    factory<SearchGitHubRepositoriesUseCase> { SearchGitHubRepositoriesUseCaseImpl(get(), get()) }
    factory<GetGitHubRepositoryUseCase> { GetGitHubRepositoryUseCaseImpl(get()) }
}