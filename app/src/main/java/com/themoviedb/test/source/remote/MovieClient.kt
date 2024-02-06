package com.themoviedb.test.source.remote

import com.themoviedb.core.base.BaseService

class MovieClient(private val service: MovieService): BaseService() {

    suspend fun getMovieGenres() = getResult {
        service.movieGenres()
    }

    suspend fun discoverMovies(page: Int, genres: String?) =
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