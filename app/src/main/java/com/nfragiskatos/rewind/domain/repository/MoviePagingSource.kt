package com.nfragiskatos.rewind.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nfragiskatos.rewind.data.mapper.toMovie
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDto
import com.nfragiskatos.rewind.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

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
        val response = try {
            repository.searchMoviesPaging(query, page)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

        return if (response.isSuccessful) {
            val body = response.body()
            val movies = body?.results?.map(MovieDto::toMovie) ?: listOf()
            val nextPage = if (body != null && page < body.totalPages) page.plus(1) else null
            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = nextPage
            )
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}