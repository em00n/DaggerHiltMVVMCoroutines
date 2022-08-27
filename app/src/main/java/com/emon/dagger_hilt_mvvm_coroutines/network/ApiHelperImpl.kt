package com.emon.dagger_hilt_mvvm_coroutines.network

import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieInfo
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getMovieDetails(id: Int, apiKey: String): Response<MovieInfo> =
        apiService.getMovieDetails(id, apiKey)
}