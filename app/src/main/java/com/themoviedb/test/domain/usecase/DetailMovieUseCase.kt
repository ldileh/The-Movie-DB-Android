package com.themoviedb.test.domain.usecase

import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.test.domain.remote.RemoteDataSource
import javax.inject.Inject

class DetailMovieUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BaseUseCase() {

    suspend fun getDetailMovie(movieId: Int) = handleResponse {
        remoteDataSource.detailMovie(movieId)
    }

    suspend fun getVideosTrailer(movieId: Int) = handleResponse {
        remoteDataSource.movieVideos(movieId)
    }
}