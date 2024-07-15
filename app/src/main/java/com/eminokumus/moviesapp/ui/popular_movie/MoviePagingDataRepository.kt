package com.eminokumus.moviesapp.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eminokumus.moviesapp.data.api.POST_PER_PAGE
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.repository.MovieDataSourceFactory
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.Movie
import kotlinx.coroutines.flow.Flow

class MoviePagingDataRepository(private val apiService: TheMovieDBInterface) {

    lateinit var  moviePagingData: Flow<PagingData<Movie>>
   // val moviePagingData: LiveData<Flow<PagingData<Movie>>> get() = _moviePagingData
    val movieDataSourceFactory: MovieDataSourceFactory = MovieDataSourceFactory(apiService)

    fun fetchLiveMoviePagingData(): LiveData<PagingData<Movie>> {

        val config = PagingConfig(pageSize = POST_PER_PAGE)
        moviePagingData =
            Pager(
                config = config,
                pagingSourceFactory = movieDataSourceFactory
            ).flow

        return moviePagingData.asLiveData()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        val networkState: LiveData<NetworkState> =
            movieDataSourceFactory.moviesLiveDataSource.switchMap {
                it.networkState
            }
        return networkState
    }
}