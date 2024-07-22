package com.eminokumus.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import com.eminokumus.moviesapp.data.vo.Movie
import javax.inject.Inject

class MovieDataSourceFactory @Inject constructor(
    private val movieDataSource: MovieDataSource
) : PagingSourceFactory<Int, Movie> {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun invoke(): PagingSource<Int, Movie> {
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}