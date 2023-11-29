package com.test.application.authorizationscreen.di

import com.test.application.authorizationscreen.BuildConfig
import com.test.application.core.repository.LoginRepository
import com.test.application.core.repository.PaymentsRepository
import com.test.application.login_screen.LoginViewModel
import com.test.application.payments_screen.PaymentsViewModel
import com.test.application.remote_data.repository.LoginRepositoryImpl
import com.test.application.remote_data.api.ApiService
import com.test.application.remote_data.repository.PaymentsRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    val interceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("app-key", BuildConfig.APP_KEY)
            .addHeader("v", BuildConfig.APP_VERSION)
            .build()
        chain.proceed(request)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<PaymentsRepository> { PaymentsRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel {LoginViewModel(get())}
    viewModel {PaymentsViewModel(get())}
}