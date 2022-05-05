package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.ui.repository.data.mapper.RepositoryUIMapper
import com.fifed.githubrepositories.ui.repository.data.mapper.impl.RepositoryUIMapperImpl
import com.fifed.githubrepositories.ui.search.data.mapper.SearchUIMapper
import com.fifed.githubrepositories.ui.search.data.mapper.impl.SearchUIMapperImpl
import org.koin.dsl.module

val uiMappersModule = module {
    factory<SearchUIMapper> { SearchUIMapperImpl(get()) }
    factory<RepositoryUIMapper> { RepositoryUIMapperImpl(get()) }
}