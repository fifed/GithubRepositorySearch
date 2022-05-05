package com.fifed.githubrepositories.di

import com.fifed.githubrepositories.network.RepositoriesApi
import com.fifed.githubrepositories.pagination.Pager
import com.fifed.githubrepositories.pagination.PagerImpl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val API_ENV = "https://api.github.com/"

val networkModule = module {
    factory { Gson() }
    factory { createHttpLoggingInterceptor() }
    factory { createOkHttpClient(get()) }
    single { createRetrofit(get(), get()) }
    factory { get<Retrofit>().create(RepositoriesApi::class.java) }
}

private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}

private fun createOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(httpLoggingInterceptor)
    return builder.build()
}

private fun createRetrofit(gson: Gson, client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(API_ENV)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()