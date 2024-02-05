package com.themoviedb.test.di

import android.content.Context
import com.themoviedb.core.utils.NetworkConnectionInterceptor
import com.themoviedb.test.BuildConfig
import com.themoviedb.test.config.GlobalConfig
import com.themoviedb.test.source.remote.MovieClient
import com.themoviedb.test.source.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClientBuilder(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().apply {
            if(GlobalConfig.IS_DEBUG)
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })

            addInterceptor(NetworkConnectionInterceptor(context))
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideRemoteClient(service: MovieService): MovieClient = MovieClient(service)
}