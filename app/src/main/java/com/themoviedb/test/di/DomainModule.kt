package com.themoviedb.test.di

import com.themoviedb.test.domain.DetailMovieUseCase
import com.themoviedb.test.domain.MainUseCase
import com.themoviedb.test.domain.UserReviewUseCase
import com.themoviedb.test.source.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DomainModule {

    @Singleton
    @Provides
    fun provideDetailMovieUseCase(movieRepository: MovieRepository): DetailMovieUseCase{
        return DetailMovieUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun provideMainMovieUseCase(movieRepository: MovieRepository): MainUseCase{
        return MainUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun provideUserReviewMovieUseCase(movieRepository: MovieRepository): UserReviewUseCase{
        return UserReviewUseCase(movieRepository)
    }
}