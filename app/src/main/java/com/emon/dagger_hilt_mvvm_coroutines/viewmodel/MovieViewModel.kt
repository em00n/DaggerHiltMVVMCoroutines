package com.emon.dagger_hilt_mvvm_coroutines.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.emon.dagger_hilt_mvvm_coroutines.R
import com.emon.dagger_hilt_mvvm_coroutines.model.movie.MovieInfo
import com.emon.dagger_hilt_mvvm_coroutines.repository.MovieRepository
import com.emon.dagger_hilt_mvvm_coroutines.utils.NetworkHelper
import com.emon.dagger_hilt_mvvm_coroutines.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val networkHelper: NetworkHelper,
    @ApplicationContext private val context: Context
) : ViewModel() {

    lateinit var movieInfoData: MutableLiveData<Resource<MovieInfo>>

    init {

    }

    fun getMovieDetails(id: Int): LiveData<Resource<MovieInfo>> {
        movieInfoData = MutableLiveData<Resource<MovieInfo>>()

        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            movieInfoData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                movieRepository.getMovieDetails(id).let {
                    if (it.isSuccessful) {
                        movieInfoData.postValue(Resource.success(it.body()))

                    } else movieInfoData.postValue(Resource.error(it.code().toString(), null))
                }
            } else movieInfoData.postValue(
                Resource.error(
                    context.getString(R.string.no_internet_connection),
                    null
                )
            )
        }
        return movieInfoData
    }

    fun getMoviesList(): LiveData<PagingData<MovieInfo>> {
        return movieRepository.getMoviesList()

    }
}