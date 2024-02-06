package com.themoviedb.test.domain

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mainUseCase: MainUseCase

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun getMovieGenres() = runBlocking {
        val movieGenres = mainUseCase.getMovieGenres()
        movieGenres
            .collectLatest {
                assertTrue("movies genres is empty", it.isNotEmpty())
            }
    }
}