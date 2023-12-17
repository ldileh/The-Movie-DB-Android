package com.themoviedb.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.core.utils.Resource
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.domain.remote.model.MovieDetailResponseModel
import com.themoviedb.test.domain.remote.model.Video
import com.themoviedb.test.domain.usecase.DetailMovieUseCase
import com.themoviedb.test.ui.model.MovieDetailModel
import com.themoviedb.test.util.extenstion.getErrorMessage
import com.themoviedb.test.util.extenstion.getImageUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val useCase: DetailMovieUseCase
): BaseViewModel() {

    private val _detailMovie = MutableLiveData<List<MovieDetailModel>>()
    val detailMovie: LiveData<List<MovieDetailModel>> = _detailMovie

    private val _bannerImage = MutableLiveData<String>()
    val bannerImage: LiveData<String> = _bannerImage

    private val _videosTrailer = MutableLiveData<List<Video>>()
    val videosTrailer: LiveData<List<Video>> = _videosTrailer

    fun getDetailMovie(movieId: Int) = launch {
        useCase.getDetailMovie(movieId).getResultCase { result ->
            when(result){
                is Resource.Success -> {
                    result.data?.let { data ->
                        _bannerImage.postValue(getImageUrl(data.posterPath))
                        _detailMovie.postValue(data.parseAsItems())
                    }
                }

                is Resource.Failure -> {
                    result.error?.let {
                        eventMessage.postValue(it.response.getErrorMessage(it.message))
                    }
                }
            }
        }
    }

    fun getVideosTrailer(movieId: Int) = launch {
        useCase.getVideosTrailer(movieId).getResultCase { result ->
            when(result){
                is Resource.Success -> {
                    // take only video from youtube
                    result.data?.results
                        ?.filter { it.site == "YouTube" }
                        .takeIf { !it.isNullOrEmpty() }
                        ?.let { data ->
                            _videosTrailer.postValue(data)
                        }
                }

                is Resource.Failure -> {
                    result.error?.let {
                        eventMessage.postValue(it.response.getErrorMessage(it.message))
                    }
                }
            }
        }
    }

    private fun MovieDetailResponseModel.parseAsItems(): List<MovieDetailModel> =
        listOf(
            MovieDetailModel.Title(title.safe()),
            MovieDetailModel.SubTitle(overview.safe()),
            MovieDetailModel.Field(
                label = "Popularity",
                value = popularity.safe().toString()
            ),
            MovieDetailModel.Field(
                label = "Release Date",
                value = releaseDate.safe()
            ),
            MovieDetailModel.Field(
                label = "Language",
                value = spokenLanguages?.joinToString { it?.name.safe() } ?: ""
            ),
        )
}