package com.themoviedb.test.domain.remote

import com.themoviedb.test.BuildConfig
import com.themoviedb.test.domain.remote.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    @GET("genre/movie/list")
    suspend fun movieGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieGenresResponseModel>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
        @Query("with_genres") genres: List<Int>? = null,
    ): Response<MovieResponseModel>

    @GET("movie/{movieId}")
    suspend fun detailMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailResponseModel>

    @GET("movie/{movieId}/reviews")
    suspend fun movieReviews(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<MovieReviewsResponseModel>

    @GET("movie/{movieId}/videos")
    suspend fun movieVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieVideosResponseModel>
}