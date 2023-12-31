package com.themoviedb.test.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.test.domain.remote.model.Review
import com.themoviedb.test.domain.usecase.UserReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReviewViewModel @Inject constructor(
    private val useCase: UserReviewUseCase
): BaseViewModel() {

    private val movieId = MutableStateFlow(-1)
    private lateinit var _responseReviews: Flow<PagingData<Review>>
    val responseReviews: Flow<PagingData<Review>>
        get() = _responseReviews

    init {
        getReviews()
    }

    fun setMovieIdNumber(id: Int) = launch{
        movieId.emit(id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getReviews() {
        viewModelScope.launch {
            movieId
                .flatMapLatest { id ->
                    useCase.userReviews(id)
                }
                .cachedIn(viewModelScope)
                .let {
                    _responseReviews = it
                }
        }
    }
}