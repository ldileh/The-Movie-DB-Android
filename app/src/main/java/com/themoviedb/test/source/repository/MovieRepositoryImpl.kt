package com.themoviedb.test.source.repository

import com.themoviedb.core.utils.Resource
import com.themoviedb.test.model.source.remote.MovieDetailResponseModel
import com.themoviedb.test.model.source.remote.MovieGenresResponseModel
import com.themoviedb.test.model.source.remote.MovieResponseModel
import com.themoviedb.test.model.source.remote.MovieReviewsResponseModel
import com.themoviedb.test.model.source.remote.MovieVideosResponseModel
import com.themoviedb.test.source.remote.MovieClient
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieClient: MovieClient
): MovieRepository {

    override suspend fun getMovieGenres(): Resource<MovieGenresResponseModel> {
        return movieClient.getMovieGenres()
    }

    override suspend fun getMovies(
        page: Int,
        genres: String?,
    ): Response<MovieResponseModel> {
        return movieClient.discoverMovies(page, genres)
    }

    override suspend fun getDetailMovie(movieId: Int): Resource<MovieDetailResponseModel> {
        return movieClient.detailMovie(movieId)
    }

    override suspend fun getMovieReviews(
        page: Int,
        movieId: Int
    ): Response<MovieReviewsResponseModel> {
        return movieClient.movieReviews(page, movieId)
    }

    override suspend fun getMovieVideos(movieId: Int): Resource<MovieVideosResponseModel> {
        return movieClient.movieVideos(movieId)
    }
}