package com.themoviedb.test.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.test.di.IoDispatcher
import com.themoviedb.test.domain.MainUseCase
import com.themoviedb.test.model.source.remote.Movie
import com.themoviedb.test.model.ui.GenreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val mainUseCase: MainUseCase
) : BaseViewModel(dispatcher) {

    private val _genreFilter = MutableStateFlow<List<Int>?>(null)
    val genreFilter: StateFlow<List<Int>?> = _genreFilter

    @OptIn(ExperimentalCoroutinesApi::class)
    val responseMovie: Flow<PagingData<Movie>>
        get() = _genreFilter
            .flatMapLatest { genres -> mainUseCase.getMovies(genres) }
            .cachedIn(viewModelScope)

    private val _itemsGenre = mutableListOf<GenreModel>()
    val itemsGenre: List<GenreModel> = _itemsGenre

    private val _triggerRefreshMovie = MutableLiveData<Boolean>()
    val triggerRefreshMovie: LiveData<Boolean> = _triggerRefreshMovie

    init{
        getMovieGenres()
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
        _genreFilter.emit(genreIdSelected?.ifEmpty { null })

        _triggerRefreshMovie.postValue(true)
    }

    private fun getMovieGenres() = launch {
        mainUseCase
            .getMovieGenres()
            .flowOn(dispatcher)
            .collectLatest {
                _itemsGenre.apply {
                    clear()
                    addAll(it)
                }
            }
    }
}