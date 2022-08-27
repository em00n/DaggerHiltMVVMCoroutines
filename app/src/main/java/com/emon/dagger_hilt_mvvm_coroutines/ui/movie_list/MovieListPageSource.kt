package com.emon.dagger_hilt_mvvm_coroutines.ui.movie_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emon.dagger_hilt_mvvm_coroutines.BuildConfig.API_KEY
import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieListResponse
import com.emon.dagger_hilt_mvvm_coroutines.network.ApiService
import com.emon.dagger_hilt_mvvm_coroutines.utils.Constants.language

class MovieListPageSource(private val apiService: ApiService) :
    PagingSource<Int, MovieInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieInfo> {
        return try {

            val pageNumber = params.key ?: 1 // current page that is being displayed
            val response: MovieListResponse =
                apiService.getMoviesList(API_KEY, language, pageNumber)

            val data: ArrayList<MovieInfo> =
                response.movieInfo as ArrayList<MovieInfo> // List of results from the API

            LoadResult.Page(
                data = data, // Provide the List<CharacterDTO>
                prevKey = null,
                nextKey = if (data.isEmpty()) null else pageNumber + 1 // As the user scrolls provide the next page number till there is no more data
            )
        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}