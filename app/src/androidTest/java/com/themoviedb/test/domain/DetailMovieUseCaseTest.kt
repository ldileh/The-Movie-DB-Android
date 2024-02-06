package com.themoviedb.test.domain

import com.themoviedb.test.model.ui.state.MovieDetailState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DetailMovieUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var detailMovieUseCase: DetailMovieUseCase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun getDetailMovie() = runBlocking {
        detailMovieUseCase.getDetailMovie(866398)
            .collectLatest {
                assertTrue(it is MovieDetailState.Success)

                if (it is MovieDetailState.Success){
                    assertNotNull("banner movie is null", it.bannerUrl)
                    assertTrue("detail data of movie is empty", it.detailData.isNotEmpty())
                    assertNotNull("videos trailer of movie is null", it.videosTrailer)
                }
            }
    }

    @Test
    fun failedGetDetailMovie() = runBlocking {
        detailMovieUseCase.getDetailMovie(-1)
            .collectLatest {
                assertTrue(
                    "State is success, it's kinda weird",
                    it is MovieDetailState.Failed
                )
                assertTrue(
                    "the error message is empty",
                    (it as MovieDetailState.Failed).message.isNotEmpty()
                )
            }
    }
}