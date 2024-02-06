package com.themoviedb.test.source.repository

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MovieRepositoryTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieRepository: MovieRepository

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun getMovieGenres() = runBlocking{
        val itemSize = movieRepository.getMovieGenres().data?.genres?.size

        assertNotNull("list item of genres is null", itemSize)
    }

    @Test
    fun successGetDetailMovie() = runBlocking{
        val data = movieRepository.getDetailMovie(866398).data
        assertNotNull("detail movie is null", data)
    }

    @Test
    fun failedGetDetailMovie() = runBlocking{
        val data = movieRepository.getDetailMovie(-1).data
        assertNull("the data detail movie is not null, even the movie id is -1", data)
    }

    @Test
    fun getMovieVideoTrailerNotEmpty() = runBlocking{
        val data = movieRepository.getMovieVideos(866398)
            .data
            ?.results
            .takeIf { !it.isNullOrEmpty() }

        assertNotNull("movie video trailer is null/empty", data)
    }

    @Test
    fun getMovieVideosEmpty() = runBlocking{
        val data = movieRepository.getMovieVideos(-1)
            .data
            ?.results
            .takeIf { !it.isNullOrEmpty() }

        assertNull(
            "the movie video trailer is not null/empty, even the movie id is -1",
            data
        )
    }

    @Test
    fun successGetMovies() = runBlocking {
        val response = movieRepository.getMovies(1, null)

        assertTrue(
            "Failed get movies, response code ${response.code()}",
            response.isSuccessful
        )
        assertNotNull(response.body()?.results)
    }

    @Test
    fun wrongNumberPageWhileGetMovies() = runBlocking {
        val response = movieRepository.getMovies(-1, null)

        assertFalse(
            "response is still success, even paging number is -1",
            response.isSuccessful
        )
        assertNull(
            "response body data is not null, even paging number is -1 ",
            response.body()
        )
    }
}