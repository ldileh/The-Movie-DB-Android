package com.themoviedb.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.core.utils.Resource
import com.themoviedb.test.domain.remote.model.Movie
import com.themoviedb.test.domain.usecase.MainUseCase
import com.themoviedb.test.ui.model.GenreModel
import com.themoviedb.test.ui.model.translate
import com.themoviedb.test.util.extenstion.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val genreFilter = MutableStateFlow<List<Int>?>(null)
    private lateinit var _responseMovie: Flow<PagingData<Movie>>
    val responseMovie: Flow<PagingData<Movie>>
        get() = _responseMovie

    private val _itemsGenre = mutableListOf<GenreModel>()
    val itemsGenre: List<GenreModel> = _itemsGenre

    private val _triggerRefreshMovie = MutableLiveData<Boolean>()
    val triggerRefreshMovie: LiveData<Boolean> = _triggerRefreshMovie

    init{
        getMovieGenres()
        getMovies()
    }

    fun setSelectedGenre(genreIdSelected: List<Int>?) = launch {
        // set temp selected item in temp genre items
        _itemsGenre.forEach { item ->
            if(genreIdSelected == null){
                item.isSelected = false
            }else if(genreIdSelected.contains(item.data.id)){
                item.isSelected = true
            }
        }
        genreFilter.emit(genreIdSelected?.ifEmpty { null })

        _triggerRefreshMovie.postValue(true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getMovies() {
        viewModelScope.launch {
            genreFilter
                .flatMapLatest { genres ->
                    mainUseCase.discoverMovies(genres)
                }
                .cachedIn(viewModelScope)
                .let {
                    _responseMovie = it
                }
        }
    }

    private fun getMovieGenres() = launch {
        mainUseCase.getMovieGenres().getResultCase { result ->
            when(result){
                is Resource.Success -> {
                    result.data?.genres?.let { genres ->
                        _itemsGenre.apply {
                            clear()
                            addAll(genres.translate())
                        }
                    }
                }

                is Resource.Failure -> {
                    result.error?.let {
                        eventMessage.postValue(it.response.getErrorMessage(it.message))
                    }
                }
                else -> {}
            }
        }
    }
}