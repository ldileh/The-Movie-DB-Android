package com.themoviedb.test.domain

import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.core.utils.Resource
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.model.source.remote.MovieDetailResponseModel
import com.themoviedb.test.model.ui.MovieDetailModel
import com.themoviedb.test.model.ui.state.MovieDetailState
import com.themoviedb.test.source.repository.MovieRepository
import com.themoviedb.test.util.ext.getImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailMovieUseCase(
    private val movieRepository: MovieRepository
): BaseUseCase() {

    suspend fun getDetailMovie(movieId: Int): Flow<MovieDetailState> {
        return flow {

            // collect data of detail response
            when(val movieResponse = handleResponse { movieRepository.getDetailMovie(movieId) }){
                is Resource.Success -> {
                    val result = MovieDetailState.Success(
                        bannerUrl = getImageUrl(movieResponse.data?.posterPath.safe()),
                        detailData = movieResponse.data.parseAsItems(),
                    )

                    // collect data of video trailer
                    val trailerResponse = handleResponse { movieRepository.getMovieVideos(movieId) }
                    if(trailerResponse is Resource.Success){
                        result.videosTrailer = trailerResponse.data?.results
                            ?.filter { it.site == "YouTube" }
                            .takeIf { !it.isNullOrEmpty() } ?: listOf()
                    }

                    emit(result)
                }

                is Resource.Failure ->
                    emit(MovieDetailState.Failed(movieResponse.error?.message.safe()))
            }
        }
    }

    private fun MovieDetailResponseModel?.parseAsItems(): List<MovieDetailModel> =
        listOf(
            MovieDetailModel.Title(this?.title.safe("-")),
            MovieDetailModel.SubTitle(this?.overview.safe()),
            MovieDetailModel.Field(
                label = "Popularity",
                value = this?.popularity.safe().toString()
            ),
            MovieDetailModel.Field(
                label = "Release Date",
                value = this?.releaseDate.safe()
            ),
            MovieDetailModel.Field(
                label = "Language",
                value = this?.spokenLanguages?.joinToString { it?.name.safe("-") } ?: ""
            ),
        )
}