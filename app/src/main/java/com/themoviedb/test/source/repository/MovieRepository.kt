package com.themoviedb.test.source.repository

import com.themoviedb.core.utils.Resource
import com.themoviedb.test.model.source.remote.MovieDetailResponseModel
import com.themoviedb.test.model.source.remote.MovieGenresResponseModel
import com.themoviedb.test.model.source.remote.MovieResponseModel
import com.themoviedb.test.model.source.remote.MovieReviewsResponseModel
import com.themoviedb.test.model.source.remote.MovieVideosResponseModel
import retrofit2.Response

interface MovieRepository {

    suspend fun getMovieGenres(): Resource<MovieGenresResponseModel>
    suspend fun getMovies(page: Int, genres: String?): Response<MovieResponseModel>
    suspend fun getDetailMovie(movieId: Int): Resource<MovieDetailResponseModel>
    suspend fun getMovieReviews(page: Int, movieId: Int): Response<MovieReviewsResponseModel>
    suspend fun getMovieVideos(movieId: Int): Resource<MovieVideosResponseModel>
}