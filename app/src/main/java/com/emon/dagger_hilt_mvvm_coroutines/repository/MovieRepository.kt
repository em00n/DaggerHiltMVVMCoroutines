package com.emon.dagger_hilt_mvvm_coroutines.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emon.dagger_hilt_mvvm_coroutines.BuildConfig
import com.emon.dagger_hilt_mvvm_coroutines.network.ApiHelper
import com.emon.dagger_hilt_mvvm_coroutines.network.ApiService
import com.emon.dagger_hilt_mvvm_coroutines.ui.movie_list.MovieListPageSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiHelper: ApiHelper
) {

    suspend fun getMovieDetails(id: Int) = apiHelper.getMovieDetails(id, BuildConfig.API_KEY)

    fun getMoviesList() =
        Pager(PagingConfig(pageSize = 10)) {
            MovieListPageSource(apiService)
        }.liveData

}