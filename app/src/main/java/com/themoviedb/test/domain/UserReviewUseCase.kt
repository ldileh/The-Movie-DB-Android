package com.themoviedb.test.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.test.model.source.remote.Review
import com.themoviedb.test.source.data.UserReviewDataSource
import com.themoviedb.test.source.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class UserReviewUseCase(private val movieRepository: MovieRepository) : BaseUseCase() {

    fun userReviews(movieId: Int): Flow<PagingData<Review>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { UserReviewDataSource(movieRepository, movieId) }
    ).flow
}