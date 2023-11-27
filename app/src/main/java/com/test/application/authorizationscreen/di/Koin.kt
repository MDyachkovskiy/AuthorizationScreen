package com.test.application.authorizationscreen.di

import com.test.application.core.repository.LoginRepository
import com.test.application.login_screen.LoginViewModel
import com.test.application.remote_data.repository.LoginRepositoryImpl
import com.test.application.remote_data.api.ApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel {LoginViewModel(get())}
}