package com.fifed.githubrepositories

import android.app.Application
import com.fifed.githubrepositories.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                viewModelModule, networkModule, repositoriesModule, mappersModule,
                uiMappersModule, blHandlersModules, systemModule
            )
        }
    }
}