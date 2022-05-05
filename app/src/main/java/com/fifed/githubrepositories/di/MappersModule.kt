package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.mapper.PaginationResultMapper
import com.fifed.githubrepositories.mapper.RepositoriesAPIMapper
import com.fifed.githubrepositories.mapper.impl.PaginationResultMapperImpl
import com.fifed.githubrepositories.mapper.impl.RepositoriesAPIMapperImpl
import org.koin.dsl.module

val mappersModule = module {
    factory<PaginationResultMapper> { PaginationResultMapperImpl() }
    factory<RepositoriesAPIMapper> { RepositoriesAPIMapperImpl() }
}