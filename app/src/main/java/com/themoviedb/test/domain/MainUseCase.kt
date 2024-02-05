package com.themoviedb.test.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themoviedb.core.base.BaseUseCase
import com.themoviedb.core.utils.Resource
import com.themoviedb.core.utils.ext.logError
import com.themoviedb.core.utils.ext.safe
import com.themoviedb.test.source.data.MovieDataSource
import com.themoviedb.test.model.source.remote.Movie
import com.themoviedb.test.model.ui.GenreModel
import com.themoviedb.test.model.ui.translate
import com.themoviedb.test.source.repository.MovieRepository
import com.themoviedb.test.util.ext.getErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainUseCase(private val movieRepository: MovieRepository): BaseUseCase() {

    fun getMovieGenres(): Flow<List<GenreModel>> = flow {
        val response = handleResponse { movieRepository.getMovieGenres() }

        var result: List<GenreModel> = listOf()
        when(response){
            is Resource.Success -> {
                result = response.data?.genres?.translate() ?: listOf()
            }

            is Resource.Failure -> {
                val error = response.error
                logError(error?.response.getErrorMessage(error?.message.safe()))
            }
        }

        emit(result)
    }

    fun getMovies(genres: List<Int>?): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { MovieDataSource(movieRepository, genres) }
    ).flow
}
