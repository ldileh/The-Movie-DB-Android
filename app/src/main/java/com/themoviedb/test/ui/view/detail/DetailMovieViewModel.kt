package com.themoviedb.test.ui.view.detail

import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.di.IoDispatcher
import com.themoviedb.test.domain.DetailMovieUseCase
import com.themoviedb.test.model.ui.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: DetailMovieUseCase
): BaseViewModel(dispatcher) {

    private val _stateUI = MutableStateFlow<MovieDetailState>(MovieDetailState.Idle)
    val stateUI = _stateUI.asStateFlow()

    fun getDetailMovie(movieId: Int) = launch {
        useCase
            .getDetailMovie(movieId)
            .flowOn(dispatcher)
            .onStart { _stateUI.update { MovieDetailState.Idle } }
            .catch { trow -> _stateUI.update { MovieDetailState.Failed(trow.message.safe()) } }
            .collectLatest { state ->
                _stateUI.update {
                    delay(150)
                    state
                }
            }
    }
}