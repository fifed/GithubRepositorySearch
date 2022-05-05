package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.ui.repository.RepositoryViewModel
import com.fifed.githubrepositories.ui.repository.RepositoryViewModelImpl
import com.fifed.githubrepositories.ui.search.SearchViewModel
import com.fifed.githubrepositories.ui.search.SearchViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<SearchViewModel> { SearchViewModelImpl(get(), get()) }
    viewModel<RepositoryViewModel> { RepositoryViewModelImpl(get(), get()) }
}