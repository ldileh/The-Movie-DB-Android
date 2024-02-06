package com.themoviedb.test.source.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themoviedb.test.model.source.remote.Movie
import com.themoviedb.test.source.repository.MovieRepository

class MovieDataSource(
    private val movieRepository: MovieRepository,
    private val genres: List<Int>?
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int?{
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: 1

        return try {
            val response = movieRepository.getMovies(
                page = pageNumber,
                genres = genres?.joinToString("|")
            )
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if (pagedResponse?.totalPages != null && pageNumber < pagedResponse.totalPages) {
                nextPageNumber = pageNumber + 1
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}