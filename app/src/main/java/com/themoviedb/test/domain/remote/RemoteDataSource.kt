package com.themoviedb.test.domain.remote

import com.themoviedb.core.base.BaseService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val service: RemoteService): BaseService() {

    suspend fun getMovieGenres() = getResult {
        service.movieGenres()
    }

    suspend fun discoverMovies(page: Int, genres: List<Int>?) =
        service.discoverMovies(page = page, genres = genres)

    suspend fun detailMovie(movieId: Int) = getResult {
        service.detailMovie(movieId = movieId)
    }

    suspend fun movieReviews(page: Int, movieId: Int) =
        service.movieReviews(page = page, movieId = movieId)

    suspend fun movieVideos(movieId: Int) = getResult {
        service.movieVideos(movieId = movieId)
    }
}