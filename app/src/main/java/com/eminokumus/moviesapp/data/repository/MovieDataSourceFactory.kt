package com.eminokumus.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.vo.Movie

class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
) : PagingSourceFactory<Int, Movie> {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun invoke(): PagingSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}