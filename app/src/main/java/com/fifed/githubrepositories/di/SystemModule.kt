package com.fifed.githubrepositories.di

import android.content.res.Resources
import com.fifed.githubrepositories.ui.resource.ResourceManager
import com.fifed.githubrepositories.ui.resource.impl.ResourceManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val systemModule = module {
    single<Resources> { androidContext().resources }
    single<ResourceManager> { ResourceManagerImpl(get()) }
}