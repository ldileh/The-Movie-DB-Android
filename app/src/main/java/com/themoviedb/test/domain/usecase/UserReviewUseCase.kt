package com.themoviedb.test.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.test.domain.remote.RemoteDataSource
import com.themoviedb.test.domain.remote.UserReviewDataSource
import com.themoviedb.test.domain.remote.model.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserReviewUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseUseCase() {

    fun userReviews(movieId: Int): Flow<PagingData<Review>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { UserReviewDataSource(remoteDataSource, movieId) }
    ).flow
}