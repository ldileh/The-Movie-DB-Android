package com.themoviedb.test.source.repository

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
        val size = movieRepository.getMovieGenres().data?.genres?.size

        assertNotNull(size)
    }

    @Test
    fun successGetDetailMovie() = runBlocking{
        val data = movieRepository.getDetailMovie(866398).data
        assertNotNull(data)
    }

    @Test
    fun failedGetDetailMovie() = runBlocking{
        val data = movieRepository.getDetailMovie(-1).data
        assertNull(data)
    }

    @Test
    fun getMovieVideosNotEmpty() = runBlocking{
        val data = movieRepository.getMovieVideos(866398)
            .data
            ?.results
            .takeIf { !it.isNullOrEmpty() }

        assertNotNull(data)
    }

    @Test
    fun getMovieVideosEmpty() = runBlocking{
        val data = movieRepository.getMovieVideos(1232289)
            .data
            ?.results
            .takeIf { !it.isNullOrEmpty() }

        assertNull(data)
    }

    @Test
    fun successGetMovies() = runBlocking {
        val response = movieRepository.getMovies(1, null)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body()?.results)
    }

    @Test
    fun wrongNumberPageWhileGetMovies() = runBlocking {
        val response = movieRepository.getMovies(-1, null)

        assertFalse(response.isSuccessful)
        assertNull(response.body())
    }
}