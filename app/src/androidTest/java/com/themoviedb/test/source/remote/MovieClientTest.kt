package com.themoviedb.test.source.remote

import com.themoviedb.core.utils.ext.safe
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MovieClientTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieClient: MovieClient

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun getMovieGenres() = runBlocking{
        val result = movieClient.getMovieGenres()
        assertNotNull(result.data)
    }

    @Test
    fun discoverMovies() = runBlocking {
        val response = movieClient.discoverMovies(1, null)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body()?.results)
    }

    @Test
    fun detailMovie() = runBlocking {
        val result = movieClient.detailMovie(866398)
        assertNotNull(result.data)
    }

    @Test
    fun failedGetDetailMovie() = runBlocking {
        val result = movieClient.detailMovie(-1)
        val message = result.error?.message.safe()

        assertNull(result.data)
        assertTrue(message.isNotEmpty())
    }

    @Test
    fun movieReviews() = runBlocking{
        val response = movieClient.movieReviews(1, 866398)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body()?.results)
    }

    @Test
    fun movieVideos() = runBlocking {
        val result = movieClient.movieVideos(866398)
        assertNotNull(result.data)
    }

    @Test
    fun failedGetMovieVideos() = runBlocking {
        val result = movieClient.movieVideos(-1)
        val message = result.error?.message.safe()

        assertTrue(message.isNotEmpty())
        assertNull(result.data)
    }
}