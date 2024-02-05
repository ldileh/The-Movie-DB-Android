package com.themoviedb.test.domain

import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.test.source.repository.MovieRepository

class DetailMovieUseCase(private val movieRepository: MovieRepository): BaseUseCase() {

    suspend fun getDetailMovie(movieId: Int) = handleResponse {
        movieRepository.getDetailMovie(movieId)
    }

    suspend fun getVideosTrailer(movieId: Int) = handleResponse {
        movieRepository.getMovieVideos(movieId)
    }
}