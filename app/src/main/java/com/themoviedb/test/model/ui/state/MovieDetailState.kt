package com.themoviedb.test.model.ui.state

import com.themoviedb.test.model.source.remote.Video
import com.themoviedb.test.model.ui.MovieDetailModel

sealed class MovieDetailState {

    data class Success(
        val bannerUrl: String? = null,
        val detailData: List<MovieDetailModel> = listOf(),
        var videosTrailer: List<Video> = listOf()
    ): MovieDetailState()

    data class Failed(val message: String): MovieDetailState()

    object Idle: MovieDetailState()
}