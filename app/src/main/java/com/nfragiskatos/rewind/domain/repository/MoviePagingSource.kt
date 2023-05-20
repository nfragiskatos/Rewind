package com.nfragiskatos.rewind.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.model.MoviePage
import com.nfragiskatos.rewind.util.Result

class MoviePagingSource(
    private val repository: MovieRepository,
    private val query: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return when (val testResult: Result<MoviePage, Throwable> =
            repository.searchMoviesPagingTest(query, page)) {
            is Result.Failure -> {
                LoadResult.Error(testResult.reason)
            }

            is Result.Success -> {
                Page(
                    data = testResult.value.results,
                    prevKey = null,
                    nextKey = testResult.value.nextPage()
                )
            }
        }
    }
}