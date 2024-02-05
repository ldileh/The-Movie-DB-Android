package com.themoviedb.test.ui.view.user_reviews

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themoviedb.core.base.BaseViewModel
import com.themoviedb.test.di.IoDispatcher
import com.themoviedb.test.model.source.remote.Review
import com.themoviedb.test.domain.UserReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReviewViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: UserReviewUseCase
): BaseViewModel(dispatcher) {

    private val movieId = MutableStateFlow(-1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val responseReviews: Flow<PagingData<Review>>
        get() = movieId
            .flatMapLatest { id -> useCase.userReviews(id) }
            .cachedIn(viewModelScope)

    fun setMovieIdNumber(id: Int) = launch{
        movieId.emit(id)
    }
}