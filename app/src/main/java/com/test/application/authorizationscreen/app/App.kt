package com.test.application.authorizationscreen.app

import android.app.Application
import com.test.application.authorizationscreen.di.networkModule
import com.test.application.authorizationscreen.di.repositoryModule
import com.test.application.authorizationscreen.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}