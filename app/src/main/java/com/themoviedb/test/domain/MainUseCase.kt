package com.themoviedb.test.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.test.source.data.MovieDataSource
import com.themoviedb.test.model.source.remote.Movie
import com.themoviedb.test.source.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MainUseCase(private val movieRepository: MovieRepository): BaseUseCase() {

    suspend fun getMovieGenres() = handleResponse {
        movieRepository.getMovieGenres()
    }

    fun getMovies(genres: List<Int>?): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { MovieDataSource(movieRepository, genres) }
    ).flow
}
