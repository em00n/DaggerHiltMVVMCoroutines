package com.emon.dagger_hilt_mvvm_coroutines.network

import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("3/movie/top_rated")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") pageIndex: Int
    ): MovieListResponse

    @GET("3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieInfo>

}