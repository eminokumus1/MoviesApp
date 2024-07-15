package com.eminokumus.moviesapp.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.Movie

class MainActivityViewModel(private val movieRepository: MoviePagingDataRepository) : ViewModel() {

    val moviePagingData: LiveData<PagingData<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagingData()
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }
    // This method needs to be written compatible with PagingData. It needs to return true if PagingData is empty.
    fun listIsEmpty(): Boolean {
          //      return moviePagedList.value?.isEmpty() ?: true
//        val mutableListOfMovies = mutableListOf<Movie>()

        return false
    }




}