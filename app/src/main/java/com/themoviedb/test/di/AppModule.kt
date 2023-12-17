package com.themoviedb.test.di

import com.themoviedb.core.base.BaseService
import com.themoviedb.test.BuildConfig
import com.themoviedb.test.config.GlobalConfig
import com.themoviedb.test.domain.remote.RemoteDataSource
import com.themoviedb.test.domain.remote.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideService() = BaseService.createService(
        serviceClass = RemoteService::class.java,
        url = BuildConfig.SERVER_URL,
        isDebug = GlobalConfig.IS_DEBUG
    )

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: RemoteService) = RemoteDataSource(service)
}